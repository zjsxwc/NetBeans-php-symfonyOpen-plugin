# netbeans-symfonyOpen-plugin-support-symfony-twig

> This plugin is modified from https://github.com/johanness/jopen

#### how to open twig file

1. Select the symfony style Twig file path like "MyBundle:Article:new_article.html.twig"
2. click symfonyOpen buttom from [Naviagte|symfonyOpen] or Your customed short key to jump to open the twig file 

#### todo

- jump to service class file by service name
- jump to the action method by url string or routing name
- let netbeans know the class type of service from `$this->get("xxx_service_name")`. we can simply append the phpdoc `/** @var SomeType $varname */` to line
