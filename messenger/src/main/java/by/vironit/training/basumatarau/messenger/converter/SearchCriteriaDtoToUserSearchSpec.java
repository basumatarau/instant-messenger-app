package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.SearchCriteriaDto;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.util.UserSpecifications;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.jpa.domain.Specification;

public class SearchCriteriaDtoToUserSearchSpec
        implements Converter<SearchCriteriaDto, Specification<User>> {

    @Override
    public Specification<User> convert(
            MappingContext<SearchCriteriaDto,
            Specification<User>> context) {
        if(context.getSource() == null){
            return null;
        }

        final String key = context.getSource().getKey();
        final Object value = context.getSource().getValue();

        switch (key){
            case "email":
                return UserSpecifications.hasEmailLike(((String) value));
            case "nick":
                return UserSpecifications.hasNickNameLike(((String) value));
            default:
                throw new UnsupportedOperationException("lookup option not supported");
        }
    }
}
