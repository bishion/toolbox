# 脚手架生成器

## 基本功能

### 自动生成脚手架代码

代码结构如下

```text
appName
  - appName-service
    - service-common
      - src/main/java
    - service-demo1
      - src/main/java
        - package.name
          - repository
            - entity
            - mapper
            - vo
            - manager
          - north
            - local
            - facade
          - south
            - adapter
            - port
          - service
            - dto
            DemoService.java
  - appName-starter
    - src
      - main
        - java
          - packageName
              AppNameApplication.java
        - resources
            application.yml
            bootstrap.yml
            logback-spring.xml
      - test  
        - java
          - packageName
              BaseAppNameTest.java
        - resources
  - appName-web
    - src
      - main
        - java
          - packageName
            - facade
            - controller
  - changelog
  pom.xml
  README.MD
  .gitignore
```

## 技术

1. spring
2. freemarker