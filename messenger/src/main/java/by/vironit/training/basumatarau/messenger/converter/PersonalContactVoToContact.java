package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.PersonalContactVo;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import by.vironit.training.basumatarau.messenger.repository.ContactEntryRepository;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonalContactVoToContact
    implements Converter<PersonalContactVo, PersonalContact> {

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    @Override
    @Transactional(readOnly = true)
    public PersonalContact convert(MappingContext<PersonalContactVo, PersonalContact> context) {
        return context.getSource() == null ? null :
                contactEntryRepository.findPersonalContactById(context.getSource().getId())
                        .orElseThrow(() -> new NoEntityFound("no contact found"));
    }
}
