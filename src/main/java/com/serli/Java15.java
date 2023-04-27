package com.serli;

public class Java15 {

    public Java15() {

        // Text Blocks
        String doc = """
            <html>
                <body>
                    <h1>Hello, world!</h1>
                </body>
            </html>""";
        System.out.println("HTML document using text block:\n" + doc);
        
        int code = 101;
        String label = "product name";
        double value = 123.123;
        String templatedJson = """
            {
                "code": %d,
                "label": "%s",
                "value": %f
            }""".formatted(code, label, value);
        System.out.println("Templated JSON document using text block:\n" + templatedJson);
        
    }
}
