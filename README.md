# demo 代码库新人导览

> 已按团队反馈补充并整理，建议作为仓库默认首页文档进行合入。

这个仓库是一个 **Maven + Java 17** 的示例集合，定位更偏“工具脚本/实验代码”，不是单一业务服务。

## 1. 仓库整体结构

- `src/main/java/kaywall/top/example/App.java`：最小入口，演示 Java 基础行为（`TreeMap` 大小写不敏感）。
- `src/main/java/kaywall/top/example/h2/`
  - `H2DatabaseExample.java`：H2 内存库 JDBC CRUD 示例。
  - `RecordStatusChecker.java`：CTE + CASE 的状态校验 SQL 示例。
- `src/main/java/kaywall/top/example/elastic/DeleteAuditIndices.java`：Elasticsearch 索引筛选与删除工具（有破坏性）。
- `src/main/java/kaywall/top/example/openfile/KeywordScanner.java`：本地目录关键字扫描工具（依赖本机文件路径）。
- `src/test/java/kaywall/top/example/AppTest.java`：JUnit3 模板测试（当前仅占位断言）。
- `pom.xml`：依赖与构建配置（Java17、ES 客户端、H2、日志等）。

## 2. 新人必须先知道的重点

1. **多入口独立运行**：每个 `main` 类都是一个独立示例，彼此不是完整调用链。
2. **环境耦合明显**：部分代码硬编码了地址、账号密码、Windows 路径。
3. **日志体系未统一**：`java.util.logging` 与 `slf4j/logback` 混用。
4. **测试覆盖不足**：目前没有覆盖核心逻辑的有效自动化测试。

## 3. 推荐上手路径（第一周）

1. 先跑构建：`mvn test`，确认本地 JDK/Maven 正常。
2. 先学 `h2` 模块：这是最容易本地复现的代码。
3. 再看 `RecordStatusChecker`：重点理解复杂 SQL 可读性写法。
4. 最后看 `elastic/openfile`：先阅读再演练，避免直接在真实环境执行。

## 4. 后续学习与改进建议

- **配置外置化（优先级最高）**：把硬编码参数迁移到配置文件/环境变量。
- **安全保护机制**：给删除索引工具增加 `dry-run`、白名单、二次确认。
- **统一日志方案**：统一到 `slf4j + logback`，减少维护复杂度。
- **补齐测试**：从 H2 示例开始写可重复单测/集成测试。
- **依赖升级路线**：升级 JUnit5，并评估 ES 客户端演进方案。

## 5. 常用命令

```bash
mvn test
mvn -DskipTests package
```

---

如要将本仓库演进为正式项目，建议先完成三件事：
1) 配置外置化；2) 基础测试补齐；3) 包结构按领域重组。
