# netbeans-symfonyOpen-plugin-support-symfony-twig

> This plugin is modified from https://github.com/johanness/jopen


A netbeans plugin to jump symfony files, twig file, Entity/Repository file, some service file

#### todo

- jump to service class file by service name
- jump to the action method by url string or routing name
- let netbeans know the class type of service from `$this->get("xxx_service_name")`. we can simply append the phpdoc `/** @var SomeType $varname */` to line
