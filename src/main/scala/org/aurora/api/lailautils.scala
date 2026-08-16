package org.aurora.api

object laikautils:
  import laika.api.Transformer
  import laika.format.{HTML, Markdown}
  import laika.config.SyntaxHighlighting

  val path: os.Path = os.pwd
  val docsBasePath = os.pwd / "target" / "docs" / "site"  

  def readme(readmeContent: String) = 
    val transformer = Transformer
        .from(Markdown)
        .to(HTML)
        .using(Markdown.GitHubFlavor, SyntaxHighlighting)
        .build
    transformer.transform(readmeContent).toOption.getOrElse("Error transforming markdown")