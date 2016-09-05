package com.proper_dom.spark

import spark.TemplateEngine
import spock.lang.Specification

class RendererSpec extends Specification {

    def "should wrap a TemplateEngine instance for convenience"() {
        given:
        Object model = Mock()
        TemplateEngine engine = Mock()
        Renderer renderer = new Renderer(engine)

        when:
        renderer.render(model, 'template.hbs')

        then:
        1 * engine.render({
            it.viewName == 'template.hbs'
            it.model == model
        })
    }
}
