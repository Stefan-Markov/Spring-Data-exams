package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureSeedRootDtos;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        String path = "src/main/resources/files/xml/pictures.xml";
        PictureSeedRootDtos pictureSeedRootDtos = this.xmlParser
                .parseXml(PictureSeedRootDtos.class, path);
        pictureSeedRootDtos.getPictures().forEach(pictureSeedDtos -> {
            if (this.validatorUtil.isValid(pictureSeedDtos)) {
                if (this.pictureRepository.findByUrl(pictureSeedDtos.getUrl()) == null) {
                    Picture picture = this.modelMapper.map(pictureSeedDtos, Picture.class);
                    this.pictureRepository.save(picture);
                    sb.append(String.format("%s successfully appended",
                            picture.getUrl())).append(System.lineSeparator());
                }
            } else {
                sb.append("Invalid picture").append(System.lineSeparator());
            }

        });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        String path = "src/main/resources/files/xml/pictures.xml";
        return String.join("", Files.readAllLines(Path.of(path)));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findByUrl(url);
    }


}
