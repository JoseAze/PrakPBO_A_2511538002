package shop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String orderId;
    private final Address shippingAddress;
    private final List<Line> lines = new ArrayList<>();

    public Order(String orderId, Address shippingAddress) {
        if (orderId == null || orderId.isBlank() || shippingAddress == null) {
            throw new IllegalArgumentException("Order id and address are required");
        }
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() { 
        return orderId; 
    }

    public Address getShippingAddress() { 
        return shippingAddress; 
    }

    public void addLine(Product product, int quantity) {
        lines.add(new Line(product, quantity));
    }

    public List<Line> getLines() { 
        return Collections.unmodifiableList(lines); 
    }

    // 1. Static Nested Class: Line
    public static class Line {
        private final Product product;
        private final int quantity;

        public Line(Product product, int quantity) {
            if (product == null || quantity <= 0) {
                throw new IllegalArgumentException("Product cannot be null and quantity must be > 0");
            }
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public long subtotal() {
            return product.getFinalPrice() * quantity;
        }
    }

    // 2. Method getItemsTotal()
    public long getItemsTotal() {
        long total = 0;
        for (Line line : lines) {
            total += line.subtotal();
        }
        return total;
    }

    // 3. Inner Class (Non-static): Receipt
    public class Receipt {
        public String toText() {
            StringBuilder sb = new StringBuilder();
            sb.append("STRUK PESANAN ").append(Order.this.orderId).append("\n");
            for (Line line : lines) {
                sb.append(line.getProduct().getName())
                  .append(" x").append(line.getQuantity())
                  .append(" = Rp").append(line.subtotal()).append("\n");
            }
            sb.append("Total barang: Rp").append(getItemsTotal()).append("\n");
            sb.append("Kirim ke: ").append(Order.this.shippingAddress);
            return sb.toString();
        }
    }

    // 4. Method createReceipt()
    public Receipt createReceipt() {
        return new Receipt();
    }
}