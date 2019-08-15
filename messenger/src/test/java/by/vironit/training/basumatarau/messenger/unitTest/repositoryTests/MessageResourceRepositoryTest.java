package by.vironit.training.basumatarau.messenger.unitTest.repositoryTests;

import by.vironit.training.basumatarau.messenger.model.*;
import by.vironit.training.basumatarau.messenger.repository.ContactEntryRepository;
import by.vironit.training.basumatarau.messenger.repository.MessageResourceRepository;
import by.vironit.training.basumatarau.messenger.repository.PrivateMessageRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

//@Profile("image-persistence-test")
public class MessageResourceRepositoryTest extends BaseRepositoryTest{

    @Autowired
    protected PrivateMessageRepository messageRepository;

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    @Autowired
    private MessageResourceRepository messageResourceRepository;

    //todo fix the test...
    //@Test
    public void whenImagePersisted_thenImageCanBeRetrieved()
            throws IOException, InstantiationException, URISyntaxException {

        final URI uri
                = getClass()
                .getResource("/test-image.jpg")
                .toURI();

        final BufferedImage image =
                ImageIO.read(
                        new File(
                                uri
                        )
                );

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();




        final User sender = users.stream().findAny().orElseThrow(() -> new RuntimeException("failed to find a user"));
        users.remove(sender);
        final User receiver = users.stream().findAny().orElseThrow(() -> new RuntimeException("failed to find a user"));

        final PersonalContact personalContact = new PersonalContact.ContactBuilder()
                .confirmed(true)
                .owner(sender)
                .person(receiver)
                .build();
        final PersonalContact personalContactTwo = new PersonalContact.ContactBuilder()
                .confirmed(true)
                .owner(receiver)
                .person(sender)
                .build();

        sender.getContactEntries().add(personalContact);
        receiver.getContactEntries().add(personalContactTwo);
        userRepository.saveAndFlush(sender);
        userRepository.saveAndFlush(receiver);

        final PrivateMessage message = new PrivateMessage.PrivateMessageBuilder()
                .author(userRepository.findByEmail(sender.getEmail()).orElseThrow())
                .body("test message")
                .contact(contactEntryRepository.findContactByOwnerAndPerson(
                        userRepository.findByEmail(sender.getEmail()).orElseThrow(),
                        userRepository.findByEmail(receiver.getEmail()).orElseThrow()
                ).orElseThrow())
                .timeSent(new Date().toInstant().toEpochMilli())
                .build();

        final ImageResource imageResource
                = new ImageResource.ImageResourceBuilder()
                .name("test-pic.jpg")
                .height(image.getHeight())
                .width(image.getWidth())
                .imageBin(byteArrayOutputStream.toByteArray())
                .message(message)
                .build();
        message.getResources().add(imageResource);

        messageRepository.saveAndFlush(message);

        final MessageResource retrievedImage
                = messageResourceRepository.findAll().stream().findAny().orElseThrow();

        assertThat(
                Arrays.equals(
                        ((ImageResource) retrievedImage).getImageBin(),
                        imageResource.getImageBin()
                )
        ).isTrue();
    }
}
