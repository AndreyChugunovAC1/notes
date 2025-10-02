var builder = WebApplication.CreateBuilder();

var app = builder.Build();

// int x = 1;
// app.Run(async context =>
// {
//   x = (x + 1) * (x + 1) % 10000;
//   await context.Response.WriteAsync($"Result: {x}");
// });

// app.Run(async(context) =>
// {
//     context.Response.StatusCode = 404;
//     await context.Response.WriteAsync("Resource Not Found");
// });

app.Run(async (context) =>
{
  context.Response.ContentType = "text/html; charset=utf-8";
  var stringBuilder = new System.Text.StringBuilder("<table>");

  foreach (var header in context.Request.Headers)
  {
    stringBuilder.Append($"<tr><td>{header.Key}</td><td>{header.Value}</td></tr>");
  }

  stringBuilder.Append($"<tr><td>Cookies:</td><td>{context.Request.Cookies}</td></tr>");
  stringBuilder.Append($"<tr><td>Host:</td><td>{context.Request.Host}</td></tr>");
  stringBuilder.Append($"<tr><td>Path:</td><td>{context.Request.Path}</td></tr>");

  stringBuilder.Append($"<tr></tr>");
  stringBuilder.Append($"<tr><td>Key</td><td>Value</td></tr>");
  foreach (var param in context.Request.Query)
  {
    stringBuilder.Append($"<tr><td>{param.Key}</td><td>{param.Value}</td></tr>");
  }
  // stringBuilder.Append($"<tr><td>Query:</td><td>{context.Request.QueryString}</td></tr>");
  stringBuilder.Append("</table>");
  await context.Response.WriteAsync(stringBuilder.ToString());

  // для автозагрузки файла:
  //    context.Response.Headers.ContentDisposition = "attachment; filename=my_image.jpg";
  // content type != html, or use tag <img>:
  // await context.Response.SendFileAsync("../images/image.jpg");
});
app.Run();