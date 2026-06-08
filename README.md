# Product Demo (Spring Boot + MongoDB)

Ứng dụng demo quản lý sản phẩm bằng Spring Boot và MongoDB. Giao diện quản trị nằm ở root và gọi REST API để hiển thị/CRUD sản phẩm.

## Yêu cầu

- Java 21
- Maven
- MongoDB (Atlas hoặc local)

## Biến môi trường (file `.env` trong thư mục `demo`)

- `MONGODB_URI` — connection string tới MongoDB (ví dụ: `mongodb+srv://<user>:<pass>@cluster0.mongodb.net/mydb?retryWrites=true&w=majority`).
- `PORT` — (tuỳ chọn) port server, mặc định `8080`.

> Lưu ý: file `.env` trong repo hiện có `PORT=3000` — app đọc giá trị này khi khởi động.

## Chạy ứng dụng (từ thư mục `demo`)

PowerShell:

```powershell
mvn -e spring-boot:run
# hoặc build rồi chạy
mvn package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Giao diện

Mở trình duyệt tới `http://localhost:<PORT>/` (ví dụ `http://localhost:3000/` nếu `PORT=3000`). Trang chính là giao diện quản lý sản phẩm (list, tìm kiếm, tạo, chỉnh sửa, xóa).

## REST API

- GET `/api/products` — lấy tất cả sản phẩm
- GET `/api/products?q=...` — tìm theo tên (case-insensitive)
- GET `/api/products/{id}` — lấy chi tiết
- POST `/api/products` — tạo mới (JSON body)
- PUT `/api/products/{id}` — cập nhật (JSON body)
- DELETE `/api/products/{id}` — xóa

JSON mẫu (`POST`/`PUT`):

```json
{
  "name": "Tên",
  "shortDesc": "Mô tả ngắn",
  "longDesc": "Mô tả dài",
  "brand": "Thương hiệu",
  "price": "$100.00",
  "imageUrl": "img/img1.png"
}
```

## Dữ liệu mẫu

Khi ứng dụng khởi động, nếu collection `products` rỗng thì `ProductService.initSampleData()` sẽ chèn dữ liệu mẫu tự động.
