package com.newton.solrindexer.controller;

import com.newton.solrindexer.model.Product;
import com.newton.solrindexer.model.TestModel;
import com.newton.solrindexer.repository.ProductRepository;
import com.newton.solrindexer.resource.ParseRequestResource;
import com.newton.solrindexer.resource.ParseResponseResource;
import com.newton.solrindexer.resource.ProductRequestResource;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping({"/newton/v1"})
public class SolrRestController {
  @Autowired ProductRepository productRepository;

  @GetMapping(
      value = {"/test"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TestModel> test() throws Exception {
    TestModel testModel = new TestModel();
    testModel.setRequestedTime(OffsetDateTime.now());
    System.out.println("testModel : " + testModel);

    return new ResponseEntity<>(testModel, HttpStatus.OK);
  }

  @GetMapping(
      value = {"/save"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> save() {
    Product phone = new Product();
    phone.setId("P0001");
    phone.setFileName("Phone");
    productRepository.save(phone);
    return new ResponseEntity<>(phone, HttpStatus.OK);
  }

  @GetMapping(
      value = {"/update"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> update() {
    Optional<Product> retrievedProduct = productRepository.findById("P0001");
    retrievedProduct.get().setFileName("Smart Phone");
    productRepository.save(retrievedProduct.get());
    return new ResponseEntity<>(retrievedProduct.get(), HttpStatus.OK);
  }

  @GetMapping(
      value = {"/delete"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> delete() {
    Optional<Product> retrievedProduct = productRepository.findById("P0001");
    productRepository.delete(retrievedProduct.get());
    return new ResponseEntity<>(retrievedProduct.get(), HttpStatus.OK);
  }

  @GetMapping(
          value = {"/deleteAll"},
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteAll() {
    productRepository.deleteAll();
    return new ResponseEntity<>( HttpStatus.NO_CONTENT);
  }
  @GetMapping(
      value = {"/getall"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> getAll() {
    Optional<Product> retrievedProduct =
        productRepository.findById("238bc6a4-cbc0-4ede-a381-9936e755dd23");
    return new ResponseEntity<>(retrievedProduct.get(), HttpStatus.OK);
  }

  @PostMapping(
      value = {"/getall"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Product>> getAllByRegex(
      @RequestBody ProductRequestResource productRequestResource) {

    System.out.println("Processing request : " + productRequestResource.toString());
    List<Product> retrievedProduct =
        productRepository.findAllByTitle(productRequestResource.getSearchTerm());
    return new ResponseEntity<>(retrievedProduct, HttpStatus.OK);
  }

  @PostMapping(
          value = {"/parse"},
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ParseResponseResource> parse(
          @RequestBody ParseRequestResource parseRequestResource) throws Exception {

   run(parseRequestResource.getDirPath());
    ParseResponseResource parseResponseResource = new ParseResponseResource();
    parseResponseResource.setDirPath(parseRequestResource.getDirPath());
    return new ResponseEntity<>(parseResponseResource, HttpStatus.OK);
  }

  @PostMapping(
      value = {"/getcustom"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Product>> getAllCustom(
      @RequestBody ProductRequestResource productRequestResource) {

    String givenSearchTerm = productRequestResource.getSearchTerm();
    String[] strings = givenSearchTerm.split(",");

    StringJoiner stringJoiner = new StringJoiner(" OR ");

    for (String string : strings) {
      stringJoiner.add(string.trim());
    }

    System.out.println("Processing request : " + productRequestResource.toString()+"\t Actual Search string : "+stringJoiner.toString());

    Page<Product> retrievedProduct =
        productRepository.findByCustomQuery(stringJoiner.toString(), PageRequest.of(0,20));
    return new ResponseEntity<>(retrievedProduct, HttpStatus.OK);
  }

  public void run(String path) throws Exception {
    fileWalkTree(Paths.get(path));
  }

  public List<Path> fileWalkTree(Path path) throws IOException {

    if (!Files.isDirectory(path)) {
      throw new IllegalArgumentException("Path must be a directory!");
    }

    List<Path> result = null;
    try (Stream<Path> walk = Files.walk(path)) {
      walk.filter(Files::isRegularFile)
          .forEach(
              file -> {
                try {
                  System.out.println("Parsing file : " + file.toAbsolutePath());
                  extractContentUsingParser(file.toAbsolutePath());
                } catch (IOException e) {
                  e.printStackTrace();
                } catch (TikaException e) {
                  e.printStackTrace();
                } catch (SAXException e) {
                  e.printStackTrace();
                }
              });
    }
    return result;
  }

  public String extractContentUsingParser(Path path)
      throws IOException, TikaException, SAXException {

    InputStream inputStream = new FileInputStream(path.toString());
    Parser parser = new AutoDetectParser();
    ContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
    Metadata metadata = new Metadata();
    ParseContext context = new ParseContext();
    Product product = new Product();
    parser.parse(inputStream, handler, metadata, context);
    System.out.println("================= START ======================");

    product.setId(UUID.randomUUID().toString());
    product.setFileName(path.toString());
    product.setFormat(metadata.get("dc:format"));
    product.setAuthor(metadata.get("Author"));
    product.setTitle(metadata.get("title"));
    product.setSubject(metadata.get("subject"));
    product.setCreatorTool(metadata.get("xmp:CreatorTool"));
    product.setDocumentID(metadata.get("xmpMM:DocumentID"));
    product.setEncrypted(metadata.get("pdf:encrypted"));
    product.setCreationDate(metadata.get("date"));
    product.setModifiedDate(metadata.get("modified"));
    product.setContent(handler.toString().replaceAll("\n","  "));
   // System.out.println("product.toString() :" + product.toString());
    productRepository.save(product);
    inputStream.close();
    System.out.println("============== END ============================");
    return handler.toString();
  }
}
