package nl.dvberkel.controller;

import nl.dvberkel.dyck.Builder;
import nl.dvberkel.dyck.Checker;
import nl.dvberkel.svg.SvgTreeTransformer;
import nl.dvberkel.tree.SvgTree;
import nl.dvberkel.tree.Tree;
import nl.dvberkel.tree.TreeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.svg.SVGSVGElement;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class TreeController {
    @Autowired
    Checker checker;

    @Autowired
    Builder builder;

    @Autowired
    TreeTransformer treeTransformer;

    @Autowired
    SvgTreeTransformer svgTreeTransformer;

    @Autowired
    Transformer transformer;

    @RequestMapping(method = RequestMethod.GET, value = "/{word:[()]+}")
    public ResponseEntity createSvg(@PathVariable String word) {
        if (checker.check(word)) {
            SVGSVGElement root = createSvgDocumentFrom(word);

            String svg;
            try {
                svg = createSvgFrom(root);
            } catch (UnsupportedEncodingException|TransformerException e) {
                return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok().header("Content-Type", "image/svg+xml").body(svg);
        } else {
           return status(HttpStatus.NOT_FOUND).build();
        }
    }

    private SVGSVGElement createSvgDocumentFrom(String word) {
        Tree tree = builder.build(word);
        SvgTree svgTree = treeTransformer.transform(tree);
        return svgTreeTransformer.transform(svgTree);
    }

    private String createSvgFrom(SVGSVGElement root) throws TransformerException, UnsupportedEncodingException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(output);
        transformer.transform(new DOMSource(root), result);
        return output.toString("utf-8");
    }
}
