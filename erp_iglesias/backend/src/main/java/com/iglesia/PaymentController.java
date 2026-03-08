package com.iglesia;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping
    public List<PaymentResponse> list(@RequestParam(name = "status", required = false) PaymentStatus status) {
        return paymentService.list(status)
                .stream()
                .map(PaymentResponse::from)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @PostMapping("/{id}/confirm")
    public PaymentResponse confirm(@PathVariable("id") Long id) {
        return PaymentResponse.from(paymentService.confirm(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @PostMapping("/{id}/fail")
    public PaymentResponse fail(@PathVariable("id") Long id) {
        return PaymentResponse.from(paymentService.fail(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @PostMapping("/{id}/retry")
    public PaymentResponse retry(@PathVariable("id") Long id) {
        return PaymentResponse.from(paymentService.retry(id));
    }

    public record PaymentResponse(
            Long id,
            String type,
            String status,
            String amount,
            int attempts,
            Long referenceId
    ) {
        public static PaymentResponse from(Payment payment) {
            return new PaymentResponse(
                    payment.getId(),
                    payment.getType().name(),
                    payment.getStatus().name(),
                    payment.getAmount().toPlainString(),
                    payment.getAttempts(),
                    payment.getReferenceId()
            );
        }
    }
}
