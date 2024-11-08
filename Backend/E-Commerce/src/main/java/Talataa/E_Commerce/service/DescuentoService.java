package Talataa.E_Commerce.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DescuentoService {

    // Constantes para los descuentos
    private static final double TIME_RANGE_DISCOUNT = 0.10;  // 10%
    private static final double RANDOM_ORDER_DISCOUNT = 0.50; // 50%
    private static final double FREQUENT_CUSTOMER_DISCOUNT = 0.05; // 5%

    private final LocalDateTime  discountStartTime;
    private final LocalDateTime discountEndTime;

    public DescuentoService() {
        this.discountStartTime = LocalDateTime.now().withHour(0).withMinute(0);
        this.discountEndTime = LocalDateTime.now().withHour(23).withMinute(59);
    }


    public double calculateOrderDiscount(Long customerId, boolean isRandomOrder, int customerOrderCount) {
        double totalDiscount = 0.0;
        LocalDateTime now = LocalDateTime.now();

        // Verificar si está dentro del rango de tiempo
        if (isWithinDiscountTime(now)) {
            // Aplicar descuento por rango de tiempo
            totalDiscount += TIME_RANGE_DISCOUNT;

            // Aplicar descuento si es orden aleatoria
            if (isRandomOrder) {
                totalDiscount += RANDOM_ORDER_DISCOUNT;
            }

            // Aplicar descuento si es cliente frecuente (más de 5 órdenes)
            if (customerOrderCount > 5) {
                totalDiscount += FREQUENT_CUSTOMER_DISCOUNT;
            }
        }

        return totalDiscount;
    }

    private boolean isWithinDiscountTime(LocalDateTime orderTime) {
        return orderTime.isAfter(discountStartTime) && orderTime.isBefore(discountEndTime);
    }

}
