package ru.job4j.magnit;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StoreTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    /**
     * Helper method for convert XML to Entries
     *
     * @param file xml file based on the Entries
     * @return Entries
     * @throws Exception can throws exception
     */
    private Entries unmarshaller(File file) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Entries) jaxbUnmarshaller.unmarshal(file);

    }

    /**
     * Check StoreSQL
     *
     * @throws Exception any exception
     */
    @Test
    public void whenGenerateSQLTableAndSaveToListEntriesThenOK() throws Exception {

        Config config = new Config();
        config.init();
        List<Entry> entries;
        List<Entry> expected = new ArrayList<>();
        expected.add(new Entry(1));
        expected.add(new Entry(2));
        try (StoreSQL storeSQL = new StoreSQL(config)) {
            storeSQL.generate(2);
            entries = storeSQL.load();
        }
        assertThat(entries, is(expected));
    }

    /**
     * Check StoreXML
     *
     * @throws Exception any exception
     */
    @Test
    public void whenConvertListEntriesToXMLThenOK() throws Exception {
        List<Entry> entries = new ArrayList<>();
        File fileXML = testFolder.newFile();
        //fill entries
        entries.add(new Entry(1));
        entries.add(new Entry(2));
        //liteSQL -> file.xml
        StoreXML storeXML = new StoreXML(fileXML);
        storeXML.save(entries);
        //file.xml -> Entries
        Entries result = unmarshaller(fileXML);
        assertThat(result.getEntries(), is(entries));
    }

    /**
     * Check ConvertXSQT and sum with use parser SaxCounter
     * it use before created sql
     *
     * @throws Exception
     */
    @Test
    public void whenConvertFromXMLtoAnotherXMLThenParseItAndSumIs3() throws Exception {

        List<Entry> entries = new ArrayList<>();
        File fileXML = testFolder.newFile();
        File schema = new File(getClass().getClassLoader().getResource("template.xsl").getFile());
        File convertedFileXML = testFolder.newFile();
        //fill entries
        entries.add(new Entry(1));
        entries.add(new Entry(2));
        //liteSQL -> file.xml
        StoreXML storeXML = new StoreXML(fileXML);
        storeXML.save(entries);
        //file.xml -> convertedFile.xml
        ConvertXSQT convertXSQT = new ConvertXSQT();
        convertXSQT.convert(fileXML, convertedFileXML, schema);
        //sum all fields from the convertedFile.xml
        SaxCounter saxCounter = new SaxCounter(convertedFileXML);
        Long sum = saxCounter.countFieldFromXML();

        assertThat(sum, is(3L));
    }

    /**
     * Summarizes fields
     *
     * @throws Exception
     */
    @Ignore
    @Test(timeout = 300_000)
    public void printToConsoleSum() throws Exception {
        Config config = new Config();
        config.init();
        List<Entry> entries;
        File fileXML = testFolder.newFile();
        File schema = new File(getClass().getClassLoader().getResource("template.xsl").getFile());
        File convertedFileXML = testFolder.newFile();

        try (StoreSQL storeSQL = new StoreSQL(config)) {
            storeSQL.generate(1000000);
            entries = storeSQL.load();


            //liteSQL -> file.xml
            StoreXML storeXML = new StoreXML(fileXML);
            storeXML.save(entries);
            //file.xml -> convertedFile.xml
            ConvertXSQT convertXSQT = new ConvertXSQT();
            convertXSQT.convert(fileXML, convertedFileXML, schema);
            //sum all fields from the convertedFile.xml
            SaxCounter saxCounter = new SaxCounter(convertedFileXML);
            Long sum = saxCounter.countFieldFromXML();

            System.out.println(sum);

            storeSQL.dropTable();
        }

    }

}