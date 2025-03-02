CREATE DATABASE PetShopManagement;
GO
USE PetShopManagement;
GO
-- Bảng Khách hàng
CREATE TABLE KhachHang (
    MaKH INT IDENTITY PRIMARY KEY,
    TenKH NVARCHAR(100),
    SDT VARCHAR(20),
    Email NVARCHAR(100),
    DiaChi NVARCHAR(255)
);
--Loại Sản Phẩm
CREATE TABLE LoaiSanPham(
	MaLSP VARCHAR(100) PRIMARY KEY,
	TenLSP NVARCHAR(100)
)
-- Bảng Sản phẩm
CREATE TABLE SanPham (
    MaSP VARCHAR(100) PRIMARY KEY,
	MaLSP VARCHAR(100),
    TenSP NVARCHAR(100),
    Gia DECIMAL(10, 2),
    SoLuong INT,
    GhiChu NVARCHAR(255),
	FOREIGN KEY (MaLSP) REFERENCES LoaiSanPham(MaLSP)
);

-- Bảng Nhân viên
CREATE TABLE NhanVien (
    MaNV INT IDENTITY PRIMARY KEY,
    TenNV NVARCHAR(100),
    SDT VARCHAR(20),
    Email NVARCHAR(100),
    ChucVu NVARCHAR(100),
	CongViec NVARCHAR(100),
    Luong DECIMAL(10, 2)
);

-- Bảng Hóa đơn
CREATE TABLE HoaDon (
    MaHD INT IDENTITY PRIMARY KEY,
    MaKH INT,
    MaNV INT,
    TongTien DECIMAL(10, 2),
    NgayTao DATE,
	TrangThai NVARCHAR(50)
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

-- Bảng Loại hình dịch vụ
CREATE TABLE LoaiDichVu (
    MaLDV VARCHAR(100) PRIMARY KEY,
    TenLDV NVARCHAR(100),
    GhiChu NVARCHAR(255)
);

-- Bảng Dịch vụ
CREATE TABLE DichVu (
    MaDV VARCHAR(100) PRIMARY KEY,
    TenDV NVARCHAR(100),
    MaLDV VARCHAR(100),
    Gia DECIMAL(10, 2),
    FOREIGN KEY (MaLDV) REFERENCES LoaiDichVu(MaLDV)
);

-- Bảng Chi tiết hóa đơn
CREATE TABLE HoaDonChiTiet (
    MaHDCT INT IDENTITY PRIMARY KEY,
    MaHD INT,
    MaSP VARCHAR(100),
    MaDV VARCHAR(100),
    SoLuong INT,
    Gia DECIMAL(10, 2),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP),
    FOREIGN KEY (MaDV) REFERENCES DichVu(MaDV)
);
--Pet
CREATE TABLE Pet (
    MaPet INT IDENTITY PRIMARY KEY,         
    TenPet NVARCHAR(100),                   
    LoaiPet NVARCHAR(100),                  
    GiongLoai NVARCHAR(100),                
    Tuoi INT,                               
    CanNang DECIMAL(5, 2),
	TrangThaiTiemChung BIT,
    MaKH INT                       
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)  
);

--TaiKhoan
CREATE TABLE DangNhap(
TaiKhoan NVARCHAR(50) PRIMARY KEY,
MatKhau NVARCHAR(50),
VaiTro BIT
)

INSERT INTO DangNhap(TaiKhoan,MatKhau,VaiTro)
VALUES ('Admin','123456',0)

INSERT INTO DangNhap(TaiKhoan,MatKhau,VaiTro)
VALUES ('Nhanvien','123456',1)

CREATE PROCEDURE sp_KhoHang
AS 
BEGIN
	SELECT
		sp.TenSP,
		sp.SoLuong TongSL,
		sp.SoLuong - ISNULL(SUM(hdct.SoLuong), 0) AS SL_ConLai,
		ISNULL(SUM(hdct.SoLuong), 0) AS SL_DaBan
	FROM SanPham sp
	LEFT JOIN HoaDonChiTiet hdct on sp.MaSP = hdct.MaSP
	WHERE sp.MaSP IS NOT NULL  
	GROUP BY sp.TenSP, sp.SoLuong
END

CREATE PROCEDURE sp_KhachHang
AS 
BEGIN
	SELECT 
		kh.TenKH,
		kh.SDT,
		kh.DiaChi,
		COUNT(hd.MaHD) TongHD_DaMua
	FROM KhachHang kh
	JOIN HoaDon hd on kh.MaKH = hd.MaKH
	GROUP BY kh.TenKH, kh.MaKH, kh.DiaChi,kh.SDT
	HAVING COUNT(hd.MaHD) >= 2
END

CREATE PROCEDURE sp_DoanhThu
AS
BEGIN
	SELECT
		NgayTao AS Ngay,
		COUNT(*) AS TongSo_HD,
		SUM(TongTien) AS TongThuNhap,
		MAX(TongTien) AS CaoNhat,
		AVG(TongTien) AS TrungBinh,
		MIN(TongTien) AS ThapNhat
	FROM HoaDon
	GROUP BY NgayTao
END

CREATE PROCEDURE sp_DoanhThu_Thang (@Month INT, @Year INT)
AS BEGIN
	SELECT
		FORMAT(NgayTao, 'yyyy-MM') AS Nam_Thang,
		COUNT(*) AS TongSo_HD,
		SUM(TONGTIEN) AS TongThuNhap,
		MAX(TONGTIEN) AS CaoNhat,
		AVG(TONGTIEN) AS TrungBinh,
		MIN(TONGTIEN) AS ThapNhat
	FROM HOADON
	WHERE MONTH(NgayTao) = @Month AND YEAR(NgayTao) = @Year
	GROUP BY FORMAT(NgayTao, 'yyyy-MM')
END


CREATE PROCEDURE sp_DoanhThu_Nam (@Year INT)
AS BEGIN
	SELECT
	    YEAR(NgayTao) AS Nam,
		COUNT(*) AS TongSo_HD,
		SUM(TONGTIEN) AS TongThuNhap,
		MAX(TONGTIEN) AS CaoNhat,
		AVG(TONGTIEN) AS TrungBinh,
		MIN(TONGTIEN) AS ThapNhat
	FROM HOADON
	WHERE YEAR(NGAYTAO) = @Year
	GROUP BY YEAR(NGAYTAO)
END
