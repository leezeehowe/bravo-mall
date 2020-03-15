package per.lee.bravo.sms.restful.body.meta;

import lombok.Data;

import java.util.List;

@Data
public class Meta {

    private String copyright;

    private List<String> authors;

    private String version;

    public Meta(String copyright, List<String> authors, String version) {
        this.copyright = copyright;
        this.authors = authors;
        this.version = version;
    }
}
