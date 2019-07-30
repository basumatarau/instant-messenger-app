package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.SubscriptionVo;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.repository.SubscriptionRepository;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionVoToSubscription
        implements Converter<SubscriptionVo, Subscription> {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription convert(MappingContext<SubscriptionVo, Subscription> context) {
        return context.getSource() == null ? null :
                subscriptionRepository.findById(context.getSource().getId())
                .orElseThrow(() -> new NoEntityFound("no subscription found"));
    }
}
