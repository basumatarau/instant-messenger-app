package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.PersonalContactVo;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.model.Contact;
import by.vironit.training.basumatarau.messenger.repository.ContactRepository;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonalContactVoToContact
    implements Converter<PersonalContactVo, Contact> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    @Transactional(readOnly = true)
    public Contact convert(MappingContext<PersonalContactVo, Contact> context) {
        return context.getSource() == null ? null :
                contactRepository.findById(context.getSource().getId())
                        .orElseThrow(() -> new NoEntityFound("no contact found"));
    }
}
