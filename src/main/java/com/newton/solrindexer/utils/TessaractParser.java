// package com.newton.solrindexer.utils;
//
// import com.newton.solrindexer.model.Product;
// import com.newton.solrindexer.repository.ProductRepository;
// import net.sourceforge.tess4j.ITessAPI;
// import net.sourceforge.tess4j.Tesseract;
// import net.sourceforge.tess4j.TesseractException;
// import org.apache.tika.exception.TikaException;
// import org.apache.tika.metadata.Metadata;
// import org.apache.tika.parser.AutoDetectParser;
// import org.apache.tika.parser.ParseContext;
// import org.apache.tika.parser.Parser;
// import org.apache.tika.sax.BodyContentHandler;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.xml.sax.ContentHandler;
// import org.xml.sax.SAXException;
//
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.List;
// import java.util.UUID;
// import java.util.stream.Stream;
//
// public class TessaractParser {
//
//
//  public  void run() throws Exception {
//    TessaractParser tessaractParser = new TessaractParser();
//
//
// tessaractParser.fileWalkTree(Paths.get("D:\\workarea\\code\\ocr-text-extractor\\src\\main\\resources\\pubmed"));
//  }
//
//  public  List<Path> fileWalkTree(Path path) throws IOException {
//
//    if (!Files.isDirectory(path)) {
//      throw new IllegalArgumentException("Path must be a directory!");
//    }
//
//    List<Path> result = null;
//    try (Stream<Path> walk = Files.walk(path)) {
//      walk.filter(Files::isRegularFile)
//          .forEach(
//              file -> {
//                try {
//                  extractContentUsingParser(file.toAbsolutePath());
//                } catch (IOException e) {
//                  e.printStackTrace();
//                } catch (TikaException e) {
//                  e.printStackTrace();
//                } catch (SAXException e) {
//                  e.printStackTrace();
//                }
//              });
//    }
//    return result;
//  }
//
//  public  String extractContentUsingParser(Path path)
//      throws IOException, TikaException, SAXException {
//
//    InputStream inputStream = new FileInputStream(path.toString());
//    Parser parser = new AutoDetectParser();
//    ContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
//    Metadata metadata = new Metadata();
//    ParseContext context = new ParseContext();
//    Product product = new Product();
//    parser.parse(inputStream, handler, metadata, context);
//    System.out.println("================= START ======================");
//
//    product.setId(UUID.randomUUID().toString());
//    product.setFileName(metadata.get("path.toString()"));
//    product.setFormat(metadata.get("dc:format"));
//    product.setAuthor(metadata.get("Author"));
//    product.setTitle(metadata.get("title"));
//    product.setSubject(metadata.get("subject"));
//    product.setCreatorTool(metadata.get("xmp:CreatorTool"));
//    // product.setDocumentID(metadata.get(""));
//    product.setEncrypted(metadata.get("pdf:encrypted"));
//    product.setCreationDate(metadata.get("date"));
//    product.setModifiedDate(metadata.get("modified"));
//    product.setContent(handler.toString());
//
//  //  productRepository.save(product);
//    inputStream.close();
//    System.out.println("============== END ============================");
//    return handler.toString();
//  }
//
//  public static String parseText() {
//    File image = new File("src/main/resources/sampleinput/sample.PNG");
//    Tesseract tesseract = new Tesseract();
//    tesseract.setDatapath("src/main/resources/traindata");
//    tesseract.setLanguage("eng");
//    tesseract.setPageSegMode(1);
//    tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_DEFAULT);
//    try {
//      return tesseract.doOCR(image);
//    } catch (TesseractException e) {
//      e.printStackTrace();
//    }
//    return null;
//  }
// }
