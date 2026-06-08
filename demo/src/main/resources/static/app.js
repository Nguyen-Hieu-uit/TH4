const apiBase = window.location.origin + '/api/products';

async function fetchProducts(q) {
  const url = q ? `${apiBase}?q=${encodeURIComponent(q)}` : apiBase;
  const res = await fetch(url);
  return res.json();
}

function el(tag, cls, txt) {
  const e = document.createElement(tag);
  if (cls) e.className = cls;
  if (txt) e.textContent = txt;
  return e;
}

async function refreshList(q) {
  const list = document.getElementById('list');
  list.innerHTML = '';
  const products = await fetchProducts(q);
  for (const p of products) {
    const card = el('div','card');
    const title = el('div','title', p.name);
    const brand = el('div','brand', p.brand + ' • ' + p.price);
    const img = el('img','thumb');
    img.src = p.imageUrl || 'img/img1.png';
    card.appendChild(img);
    card.appendChild(title);
    card.appendChild(brand);
    card.addEventListener('click', () => openEditor(p));
    list.appendChild(card);
  }
}

function openEditor(p) {
  const editor = document.getElementById('editor');
  editor.classList.remove('hidden');
  document.getElementById('editorTitle').textContent = p && p.id ? 'Edit Product' : 'New Product';
  const form = document.getElementById('form');
  form.id.value = p && p.id ? p.id : '';
  form.name.value = p ? p.name : '';
  form.shortDesc.value = p ? p.shortDesc : '';
  form.longDesc.value = p ? p.longDesc : '';
  form.brand.value = p ? p.brand : '';
  form.price.value = p ? p.price : '';
  form.imageUrl.value = p ? p.imageUrl : '';
  document.getElementById('btnDelete').classList.toggle('hidden', !(p && p.id));
}

function closeEditor() {
  document.getElementById('editor').classList.add('hidden');
}

async function saveForm(ev) {
  ev.preventDefault();
  const form = ev.target;
  const id = form.id.value;
  const payload = {
    name: form.name.value,
    shortDesc: form.shortDesc.value,
    longDesc: form.longDesc.value,
    brand: form.brand.value,
    price: form.price.value,
    imageUrl: form.imageUrl.value,
  };
  if (id) {
    await fetch(`${apiBase}/${id}`, {
      method: 'PUT', headers: {'Content-Type':'application/json'}, body: JSON.stringify(payload)
    });
  } else {
    await fetch(apiBase, {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(payload)});
  }
  closeEditor();
  await refreshList(document.getElementById('search').value);
}

async function deleteCurrent() {
  const form = document.getElementById('form');
  const id = form.id.value;
  if (!id) return;
  if (!confirm('Delete this product?')) return;
  await fetch(`${apiBase}/${id}`, {method:'DELETE'});
  closeEditor();
  await refreshList(document.getElementById('search').value);
}

window.addEventListener('DOMContentLoaded', () => {
  refreshList();
  document.getElementById('btnNew').addEventListener('click', () => openEditor(null));
  document.getElementById('btnCancel').addEventListener('click', closeEditor);
  document.getElementById('form').addEventListener('submit', saveForm);
  document.getElementById('btnDelete').addEventListener('click', deleteCurrent);
  document.getElementById('search').addEventListener('input', (e) => refreshList(e.target.value));
});
