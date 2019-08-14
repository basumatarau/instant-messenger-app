package by.vironit.training.basumatarau.messenger.dto.page;

import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.jsonSerializer.PageOfUserProfilesSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

@JsonSerialize(using = PageOfUserProfilesSerializer.class)
public class PageOfUserProfileDtos extends PageImpl<UserProfileDto> {
    public PageOfUserProfileDtos(List<UserProfileDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
