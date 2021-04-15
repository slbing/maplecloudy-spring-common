# maplecloudy-spring-common
SpringCloud 开发的自定义基本组件
## ResourceServer 默认配置
## 基于ResourceTree的前后端权限控制组件
## jackson的基本配置
···yml
jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    default-property-inclusion: NON_NULL
    deserialization:
      fail-on-ignored-properties: false
      fail-on-unknown-properties: false
    serialization:
      fail-on-empty-beans: false
    time-zone: Asia/Shanghai
···
## 基于Redis的分布式锁实现和配置
