package per.lee.bravo.bsonapi.body.meta;

import lombok.Data;

@Data
public class Pagination {

    private Long totalPages;

    private Long total;

    private Long current;

    private Long size;

}
