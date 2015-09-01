package nl.dvberkel.context;

import nl.dvberkel.dyck.Builder;
import nl.dvberkel.dyck.Checker;
import nl.dvberkel.svg.SvgTreeTransformer;
import nl.dvberkel.tree.TreeTransformer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.transform.Transformer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ApplicationContextTest {
    private ApplicationContext context;

    @Before
    public void createApplicationContext() {
        context = new AnnotationConfigApplicationContext(TreeContextConfiguration.class);
    }

    @Test
    public void shouldBeCreatedFromAConfiguration() {
        assertThat(context, is(notNullValue()));
    }

    @Test
    public void shouldHaveAChecker() {
        Checker checker = context.getBean(Checker.class);

        assertThat(checker, is(notNullValue()));
    }

    @Test
    public void shouldHaveABuilder() {
        Builder builder = context.getBean(Builder.class);

        assertThat(builder, is(notNullValue()));
    }

    @Test
    public void shouldHaveATreeTransformer() {
        TreeTransformer transformer = context.getBean(TreeTransformer.class);

        assertThat(transformer, is(notNullValue()));
    }

    @Test
    public void shouldHaveASvgTreeTransformer() {
        SvgTreeTransformer transformer = context.getBean(SvgTreeTransformer.class);

        assertThat(transformer, is(notNullValue()));
    }

    @Test
    public void shouldHaveATransformer() {
        Transformer transformer = context.getBean(Transformer.class);

        assertThat(transformer, is(notNullValue()));
    }
}
