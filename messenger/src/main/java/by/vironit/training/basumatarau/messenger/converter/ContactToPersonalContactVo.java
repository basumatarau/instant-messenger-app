package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.*;
import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactToPersonalContactVo implements Converter<PersonalContact, ContactEntryVo> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonalContactVo convert(MappingContext<PersonalContact, ContactEntryVo> context) {
        return context.getSource() == null ? null :
                new PersonalContactVo(context.getSource().getId(),
                        modelMapper.map(context.getSource().getOwner(), UserProfileDto.class),
                        modelMapper.map(context.getSource().getPerson(), UserProfileDto.class),
                        context.getSource().getIsConfirmed());
    }
}
