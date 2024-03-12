package mapping.mappers;

import mapping.dtos.PublicInfoToyDTO;
import model.Toy;

public class ToyMapper {

        public PublicInfoToyDTO fromEntityToDTO(Toy toy){
            return new PublicInfoToyDTO(toy.toyName, toy.toyPrice);
        }

        public Toy fromDTOtoEntity(PublicInfoToyDTO toyDTO){
            Toy toy = new Toy();
            toy.setToyName(toyDTO.toyName());
            toy.setToyPrice(toyDTO.price());
            return toy;
        }

    }

