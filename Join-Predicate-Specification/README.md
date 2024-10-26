# Article Filter API

## Giới thiệu

Xây dựng một API lọc dữ liệu linh hoạt theo các tiêu chí khác nhau. Trong dự án này tạo ra một API để lọc các sản phẩm (`Article`) dựa trên các tiêu chí như **giá**, **kích thước**, **danh mục**, **thương hiệu**, và từ khóa tìm kiếm.


## Chức năng chính

- Lọc các bài viết theo **khoảng giá** (`priceLow`, `priceHigh`)
- Lọc bài viết theo các **kích thước** nhất định (`sizes`)
- Lọc bài viết theo **danh mục** (`categories`)
- Lọc bài viết theo **thương hiệu** (`brands`)
- Tìm kiếm các bài viết theo **từ khóa** (`search`)

## Công nghệ sử dụng

Dự án này sử dụng các công nghệ và thư viện sau:

- **Spring Boot**
- **Spring Data JPA**
- **MySQL Database**
- **Spring Data JPA Specifications**: Để tạo các bộ lọc động dựa trên các tiêu chí khác nhau

## Cấu trúc dự án

- `Article`: Entity chính đại diện cho các sản phẩm.
- `Brand`, `Category`, `Size`: Các entity phụ liên kết với `Article` để quản lý **thương hiệu**, **danh mục**, và **kích thước** của sản phẩm.
- `ArticleSpecification`: Chứa logic lọc các sản phẩm dựa trên các tiêu chí khác nhau.
- `ArticleService`: Service lớp trung gian giữa controller và repository để xử lý logic nghiệp vụ.
- `ArticleController`: Controller cung cấp endpoint `/api/articles/filter` để truy vấn sản phẩm theo các tiêu chí.
