package br.com.gestup.gestup.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.PaymentMethodDTO;
import br.com.gestup.gestup.dto.request.UpsertPaymentMethodRequest;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.PaymentMethod;
import br.com.gestup.gestup.repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final IMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository,
            IMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public PaymentMethodDTO create(UpsertPaymentMethodRequest request) {
        return paymentMethodMapper.entityToDTO(this.paymentMethodRepository.save(new PaymentMethod(request.name())));
    }

    @PreAuthorize("hasAuthority('SCOPE_admin') || hasAuthority('SCOPE_seller')")
    public PaymentMethodDTO find(String id) throws UsernameNotFoundException {
        return this.paymentMethodMapper.entityToDTO(this.findPaymentMethodIfExists(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_admin') || hasAuthority('SCOPE_seller')")
    public List<PaymentMethodDTO> findAll() {
        return this.paymentMethodRepository.findAll().stream().map(this.paymentMethodMapper::entityToDTO).toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public PaymentMethodDTO update(String id, UpsertPaymentMethodRequest request) {
        PaymentMethod paymentMethod = this.findPaymentMethodIfExists(id);
        paymentMethod.setName(request.name());
        return this.paymentMethodMapper.entityToDTO(this.paymentMethodRepository.save(paymentMethod));
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public void delete(String id) {
        this.paymentMethodRepository.deleteById(id);
    }

    private PaymentMethod findPaymentMethodIfExists(String id) throws UsernameNotFoundException {
        PaymentMethod paymentMethod = this.paymentMethodRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Payment Method Not Found."));
        return paymentMethod;
    }
}
