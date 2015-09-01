package nl.dvberkel.context;

import nl.dvberkel.dyck.Builder;
import nl.dvberkel.dyck.Checker;
import nl.dvberkel.svg.SvgTreeTransformer;
import nl.dvberkel.tree.TreeTransformer;
import nl.dvberkel.tree.Configuration;
import org.springframework.context.annotation.Bean;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import static nl.dvberkel.tree.Configuration.configuration;

@org.springframework.context.annotation.Configuration
public class TreeContextConfiguration {
    @Bean
    public Builder createBuilder() {
        return new Builder(createChecker());
    }

    @Bean
    public Checker createChecker() {
        return new Checker("(", ")");
    }

    @Bean
    public TreeTransformer createTreeTransformer() {
        return new TreeTransformer(createConfiguration());
    }

    private Configuration createConfiguration() {
        return configuration()
                .withNodeRadius(20)
                .withLeafRadius(3)
                .withPadding(5);
    }

    @Bean
    public SvgTreeTransformer createSvgTreeTransformer() {
        return new SvgTreeTransformer(createConfiguration());
    }

    @Bean
    public Transformer createTransformer() throws TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        return factory.newTransformer();
    }
}
