package per.lee.bravo.mall.authorization.restful.body.meta;

import lombok.Data;

import java.util.List;

@Data
public class Meta {

    private String copyright;

    private List<String> authors;

    private Object data;
}
