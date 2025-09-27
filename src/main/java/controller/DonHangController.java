import com.shopcomputer.model.DonHang;
import com.shopcomputer.service.DonHangService;
import javax.swing.*;
import java.util.List;

public class DonHangController {

    private DonHangService donHangService;

    public DonHangController(DonHangService service) {
        this.donHangService = service;
    }

    // Lấy danh sách đơn hàng và hiển thị trong JOptionPane (ví dụ đơn giản)
    public void hienThiDanhSachDonHang() {
        try {
            List<DonHang> ds = donHangService.getAllDonHang();

            StringBuilder sb = new StringBuilder();
            for (DonHang dh : ds) {
                sb.append("Mã ĐH: ").append(dh.getMaDh())
                  .append(", Khách hàng: ").append(dh.getMaKh())
                  .append(", Tổng tiền: ").append(dh.getTongTien() == null ? "0" : dh.getTongTien().toPlainString())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "Chưa có đơn hàng nào");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Thêm đơn hàng đơn giản (trong thực tế có thể lấy từ form)
public void themDonHang(DonHang dh) {
    try {
        donHangService.addDonHang(dh);
        JOptionPane.showMessageDialog(null, "Thêm đơn hàng thành công!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        e.printStackTrace();
    }
}

}