package by.vironit.training.basumatarau.messengerService.converter;

import by.vironit.training.basumatarau.messengerService.dto.*;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactToPersonalContactVo implements Converter<Contact, ContactEntryVo> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonalContactVo convert(MappingContext<Contact, ContactEntryVo> context) {
        return context.getSource() == null ? null :
                new PersonalContactVo(context.getSource().getId(),
                        modelMapper.map(context.getSource().getOwner(), UserProfileDto.class),
                        modelMapper.map(context.getSource().getPerson(), UserProfileDto.class),
                        context.getSource().getIsConfirmed());
    }
}
