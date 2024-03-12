package mapping.dtos;

public record PublicInfoToyDTO (
        String toyName,
        double price
){}

/*

la capa de servicios o de aplicación es donde reside el núcleo del negocio, y es importante mantenerla lo más independiente posible de detalles de implementación como los DTOs. En esta capa, los servicios deben operar con objetos del dominio y no estar directamente expuestos a los detalles de la infraestructura o las representaciones de datos específicas del exterior.
Por lo tanto, es una buena práctica que en esta capa no entren ni salgan DTOs. En cambio, los DTOs se utilizan como intermediarios entre las capas de adaptación (por ejemplo, las capas de presentación o de infraestructura) y la capa de aplicación.

El controller (o controlador) en el contexto de una aplicación web, por ejemplo, sería responsable de mapear los DTOs recibidos desde el exterior a objetos del dominio con los que los servicios de aplicación pueden trabajar. De igual manera, sería res
sería responsable de convertir los resultados de las operaciones de negocio en DTOs para enviarlos de vuelta al exterior.
 */