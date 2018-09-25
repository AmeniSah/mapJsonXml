/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gesis.dataverse.mapjsonxml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.tika.Tika;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Main {

    static String org = System.getProperty("user.dir") + "/dataverse_nl.json";
    static String dest = System.getProperty("user.dir");

    // to test without description (as an example)
    static String org2 = System.getProperty("user.dir") + "/dataverse_nl2.json";

    // to test without author affiliation (as an example)
    static String org3 = System.getProperty("user.dir") + "/dataverse_nl3.json";

    public static void main(String[] args) throws IOException, ParseException {
        // This is the mapping fuction of the dataverse json model to the da-ra xml model
        DataverseEU2XMLDara(org, dest);
    }

    private static void DataverseEU2XMLDara(String jsonFilePath, String xmlFilePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(jsonFilePath));
        Response resp = new Response(object);

        // Root of the XML file        
        String daraHeader = "resource";
        Element root = new Element(daraHeader);
        Document doc = new Document();


        /* Fields list */
        
        /* create the element Title */
        Element titles = new Element("titles");

        /* create the element Creators */
        Element creators = new Element("creators");

        /* create the element Free Keywords */
        Element freeKeywords = new Element("freeKeywords");

        /* create the element Descriptions */
        Element descriptions = new Element("descriptions");

        /* create the element Publications */
        Element publications = new Element("publications");

        // Get all the elemets: descriptions, creators, keywords, titles and  publications
        ArrayList<Field> Fields = resp.getData().getLatestVersion().getCitation().getFields();
        init_fields(descriptions, creators, freeKeywords, titles, publications, Fields);

        // Get the Resrouce Type from the first dataset
        String firstFileName = resp.getData().getLatestVersion().getFiles().get(0).getDataFileModel().getFilename();
        if (firstFileName != null) {
            Element resourceType = new Element("resourceType");
            // Get the resource type
            init_resourceType(resourceType, firstFileName);
            root.addContent(resourceType);
        }

        /* create and get the Resource Identifier */
        String resourceIdIdentifierValue = resp.getData().getIdentifier();
        if (resourceIdIdentifierValue != null) {
            Element resourceIdentifier = new Element("resourceIdentifier");
            long VersionNumber = resp.getData().getLatestVersion().getVersionNumber();
            long MinorNumber = resp.getData().getLatestVersion().getVersionMinorNumber();
            String versionSeperator = ".";
            String resourceIdcurrentVersion = VersionNumber + versionSeperator + MinorNumber;

            resourceIdentifier.addContent(new Element("identifier").addContent(resourceIdIdentifierValue));
            resourceIdentifier.addContent(new Element("currentVersion").addContent(resourceIdcurrentVersion));
            root.addContent(resourceIdentifier);

            /* insert the title if not null */
            if (!titles.getChildren().isEmpty()) {
                root.addContent(titles);
            }

            /* insert the creators if not null */
            if (!creators.getChildren().isEmpty()) {
                root.addContent(titles);
            }

            /* create and get the URL */
            String resourcePersistentURL = resp.getData().getPersistentUrl();
            if (resourcePersistentURL != null) {
                Element dataURLs = new Element("dataURLs");
                dataURLs.addContent(new Element("dataURL").addContent(resourcePersistentURL));
                //add URL to xml
                root.addContent(dataURLs);
            }


            /* DOI Proposal */
            String doiSeperator = "/";
            String AuthoriyValue = resp.getData().getAuthority();
            String doiProposalValue = AuthoriyValue + doiSeperator + resourceIdIdentifierValue;
            Element doiProposal = new Element("doiProposal");
            doiProposal.addContent(doiProposalValue);
            // add to xml
            root.addContent(doiProposal);
        }
        /* create and get the Publication Date */
        String resourcePublicationDate = resp.getData().getPublicationDate();
        if (resourcePublicationDate != null) {
            Element publicationDate = new Element("publicationDate");
            publicationDate.addContent(new Element("date").addContent(resourcePublicationDate));
            // add publication date to xml
            root.addContent(publicationDate);
        }

        /* create and get the Availibility */
        Element availibility = new Element("availibility");
        Element availabilityType = new Element("availibilityType");
        String availabilityTypeValue;
        if (!resp.getData().getLatestVersion().getFiles().isEmpty()) {
            availabilityTypeValue = "Download";
        } else {
            availabilityTypeValue = "Not available";
        }
        availabilityType.addContent(availabilityTypeValue);
        availibility.addContent(availabilityType);
        // add to xml
        root.addContent(availibility);

        /* create and ge the Rights */
        String licenceTypeValue = resp.getData().getLatestVersion().getLicense();
        String termsOfAccessValue = resp.getData().getLatestVersion().getTermsOfAccess();

        if (licenceTypeValue != null) {
            Element rights = new Element("rights");
            Element licenceType = new Element("licenseType").addContent(licenceTypeValue);
            // add License type to xml
            rights.addContent(licenceType);

            if (termsOfAccessValue != null) {

                Element right = new Element("right");

                Element rightLanguage = new Element("language").addContent("en");
                Element rightFreetext = new Element("freetext").addContent(termsOfAccessValue);
                // add Right to xml
                right.addContent(rightLanguage);
                right.addContent(rightFreetext);
                rights.addContent(right);
                root.addContent(rights);
            }
        }

        /* insert Keywords if not null */
        if (!freeKeywords.getChildren().isEmpty()) {
            root.addContent(freeKeywords);
        }

        /* insert Descriptions if not null */
        if (!descriptions.getChildren().isEmpty()) {
            root.addContent(descriptions);
        }

        /* create the element Datasets */
        if (!resp.getData().getLatestVersion().getFiles().isEmpty()) {
            Element dataSets = new Element("dataSets");

            // Get the datasets
            init_files(dataSets, resp.getData().getLatestVersion().getFiles());
            // add datasets to xml
            root.addContent(dataSets);
        }

        /* insert Publications if not null */
        if (!publications.getChildren().isEmpty()) {
            root.addContent(publications);
        }

        /* xml document creation */
        doc.setRootElement(root);
        XMLOutputter outter = new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        outter.output(doc, new FileWriter(new java.io.File("drara_xml.xml")));
    }

    // get the datasets files: name, format, size
    private static void init_files(Element dataSets, ArrayList<org.gesis.dataverse.mapjsonxml.File> listFiles) {
        for (int i = 0; i < listFiles.size(); i++) {
            // get datasets' values
            DataFileModel dataFileModel = listFiles.get(i).getDataFileModel();
            String fileName = dataFileModel.getFilename();
            String fileFormat = dataFileModel.getContentType();
            String fileSize = String.valueOf(dataFileModel.getFilesize());

            // add datasets to xml
            Element dataSet = new Element("dataSet");
            Element files = new Element("files");
            Element file = new Element("file");
            file.addContent(new Element("name").addContent(fileName));
            file.addContent(new Element("format").addContent(fileFormat));
            file.addContent(new Element("size").addContent(fileSize));
            files.addContent(file);
            dataSet.addContent(files);
            dataSets.addContent(dataSet);

        }

    }

    private static void init_fields(Element descriptions, Element creators, Element freeKeywords, Element titles, Element publications, ArrayList<Field> fields) {
        for (Field field : fields) {
            switch (field.getTypeName()) {
                /**
                 * * Title ***
                 */

                case "title":
                    // get title
                    String titleValue = (String) field.getValue();
                    if (titleValue != null) {
                        Element title = new Element("title");
                        title.addContent(new Element("language").addContent("en"));
                        Element titleName = new Element("titleName");
                        try {
                            titleName.addContent((String) field.getValue());
                        } catch (ClassCastException castException) {
                            titleName.addContent("" + castException.getMessage());
                        }
                        // add title to xml
                        title.addContent(titleName);
                        titles.addContent(title);
                    }
                    break;

                /**
                 * * Creator ***
                 */
                case "author":
                    // get Creator element
                    if ((null != field) && !field.isEmpty()) {
                        Element creator = new Element("creator");
                        if (field.getValue() != null && field.getValue() instanceof JSONArray) {
                            JSONObject authorValues = (JSONObject) ((JSONArray) field.getValue()).get(0);
                            // get author and institution properties
                            init_author(creator, authorValues);

                            creators.addContent(creator);
                        }
                    }
                    break;

                case "datasetContact":
                    break;

                /**
                 * * Description ***
                 */
                case "dsDescription":
                    if (field != null && !field.isEmpty()) {
                        // get Description value and add to xml
                        Element description = new Element("description");
                        description.addContent(new Element("language").addContent("en"));
                        if (field.getValue() != null && field.getValue() instanceof JSONArray) {
                            JSONObject desObj = (JSONObject) ((JSONArray) field.getValue()).get(0);
                            String descriptionValue = (String) ((JSONObject) desObj.get("dsDescriptionValue")).get("value");
                            description.addContent(new Element("freetext").addContent(descriptionValue));
                            description.addContent(new Element("descriptionType").addContent("Abstract"));
                            descriptions.addContent(description);

                        }
                    }
                    break;

                case "subject":
                    break;

                /**
                 * * Keywords ***
                 */
                case "keyword":
                    if (field != null && !field.isEmpty()) {

                        // get Keywords' values
                        if ((JSONArray) field.getValue() != null) {
                            // create Keywords element 
                            Element freeKeyword = new Element("freeKeyword");
                            Element keywords = new Element("keywords");
                            init_freeKeywords(keywords, (JSONArray) field.getValue());
                            // add Keywords to xml
                            freeKeyword.addContent(keywords);
                            freeKeywords.addContent(freeKeyword);
                        }

                    }
                    break;

                /**
                 * * Publications ***
                 */
                case "publication":
                    // create Publication element 
                    if (field != null && !field.isEmpty()) {
                        JSONArray publicationsList = (JSONArray) field.getValue();
                        if (publicationsList != null && !publicationsList.isEmpty()) {
                            for (int i = 0; i < publicationsList.size(); i++) {

                                //JSONObject publicationValues = (JSONObject) ((JSONArray) field.getValue()).get(0);
                                JSONObject publicationValues = (JSONObject) publicationsList.get(i);
                                // get Publication values
                                init_publications(publications, publicationValues);
                            }
                        }
                    }

                    break;
                case "depositor":
                    break;
                case "dateOfDeposit":
                    break;
            }
        }

    }

    private static void init_author(Element creator, JSONObject authorValues) {
        /**
         * * Creator ***
         */

        Element person = new Element("person");
        // get author first and last name

        if (authorValues.containsKey("authorName")) {
            String fullName = (String) ((JSONObject) authorValues.get("authorName")).get("value");
            if (authorValues.get("authorName") != null) {
                Element firstName = new Element("firstName");
                Element lastName = new Element("lastName");

                String[] nameParts = fullName.split("\\s*(=>|,|\\s)\\s*");

                firstName.addContent(nameParts[0]);
                lastName.addContent(nameParts[1]);

                // add author name to XML
                person.addContent(firstName);
                person.addContent(lastName);

            }
        }

        // get author IDs
        if (authorValues.containsKey("authorIdentifier")) {
            Element personIDs = new Element("personIDs");
            Element personID = new Element("personID");
            if (authorValues.get("authorIdentifier") != null) {
                Element identifierSchema = new Element("identifierSchema");
                Element identifierURI = new Element("identifierURI");
                String identifierSchemaValue = "";
                identifierSchemaValue = (String) ((JSONObject) authorValues.get("authorIdentifierScheme")).get("value");
                identifierSchema.addContent(identifierSchemaValue);

                String identifierURIValue = "";
                String startURL = "https://";
                String endURL = ".org/";
                if (identifierSchemaValue.toLowerCase().equals("viaf") || identifierSchemaValue.toLowerCase().equals("orcid")) {
                    String identifierURINum = (String) ((JSONObject) authorValues.get("authorIdentifier")).get("value");
                    identifierURIValue = startURL + identifierSchemaValue.toLowerCase() + endURL + identifierURINum;
                }
                identifierURI.addContent(identifierURIValue);

                // add author ID to XML
                personID.addContent(identifierURI);
                personID.addContent(identifierSchema);
                personIDs.addContent(personID);
                person.addContent(personIDs);
            }
        }
        // add author to XML
        creator.addContent(person);

        /**
         * * Institution ***
         */
        // get Institution properties
        if (authorValues.containsKey("authorAffiliation")) {
            String authorAffiliation = (String) ((JSONObject) authorValues.get("authorAffiliation")).get("value");
            if (authorValues.get("authorAffiliation") != null) {
                Element institution = new Element("institution");
                Element institutionName = new Element("institutionName");

                // add Institution to XML
                institutionName.addContent(authorAffiliation);
                institution.addContent(institutionName);
                creator.addContent(institution);
            }
        }
    }

    private static void init_freeKeywords(Element keywords, JSONArray keywordsFromJson) {
        if (!keywordsFromJson.isEmpty() && keywordsFromJson instanceof JSONArray) {
            for (Object keywordFromJson : keywordsFromJson) {
                Element keyword = new Element("keyword");
                JSONObject keywordValue = (JSONObject) ((JSONObject) keywordFromJson).get("keywordValue");
                if (keywordValue != null) {
                    keyword.addContent((String) keywordValue.get("value"));
                    keywords.addContent(keyword);
                }

            }
        }
    }

    private static void init_resourceType(Element resourceType, String firstFileName) {
        /* Resource Type */
        String resourceTypeValue;

        // mime type checking from files
        Tika tika = new Tika();

        String mimetype;
        mimetype = tika.detect(firstFileName);
        if (mimetype.contains("text")) {
            resourceTypeValue = "Text";

        } else if (mimetype.contains("video")) {
            resourceTypeValue = "Audiovisual";

        } else if (mimetype.contains("image")) {
            resourceTypeValue = "Image";

        } else if (mimetype.contains("audio")) {
            resourceTypeValue = "Sound";

        } else {
            resourceTypeValue = "Other";

        }

        //add Resource Type to xml
        resourceType.addContent(resourceTypeValue);
    }

    private static void init_publications(Element publications, JSONObject publicationValues) {
        // create elements of publication and get values
        Element publication = new Element("publication");
        Element unstructuredPublication = new Element("unstructuredPublication");
        Element publicationFreeText = new Element("freetext");
        Element publicationPIDs = new Element("PIDs");
        Element publicationPID = new Element("PID");
        Element publicationID = new Element("ID");
        Element publicationPidType = new Element("pidType");
        String publicationFreeTextValue = (JSONObject) publicationValues.get("publicationCitation") != null ? (String) ((JSONObject) publicationValues.get("publicationCitation")).get("value") : "";
        String publicationPidTypeValue = (JSONObject) publicationValues.get("publicationIDType") != null ? (String) ((JSONObject) publicationValues.get("publicationIDType")).get("value") : "";
        String publicationIDValue = (JSONObject) publicationValues.get("publicationIDNumber") != null ? (String) ((JSONObject) publicationValues.get("publicationIDNumber")).get("value") : "";
        //add publication to xml: id, PID, text
        // publication text
        publicationFreeText.addContent(publicationFreeTextValue);
        unstructuredPublication.addContent(publicationFreeText);
        // publication id and PID
        publicationPidType.addContent(publicationPidTypeValue);
        publicationID.addContent(publicationIDValue);
        publicationPID.addContent(publicationID);
        publicationPID.addContent(publicationPidType);
        publicationPIDs.addContent(publicationPID);
        unstructuredPublication.addContent(publicationPIDs);
        // add all publication values to xml
        publication.addContent(unstructuredPublication);
        publications.addContent(publication);
    }
}
