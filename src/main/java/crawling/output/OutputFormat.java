package crawling.output;

/**
 * Created by midori on 2016/04/24.
 */
public enum OutputFormat {
    CONSOLE("コンソール"),
    TXT("テキストファイル"),
    CSV("CSVファイル");

    private String name;

    OutputFormat(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
