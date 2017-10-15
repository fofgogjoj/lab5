package ru.bstu.it31.mishchenko.lab5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class list {
    public static void writeDOM(ArrayList<String> listArray, Scanner scanner){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("Список_учащихся");
            doc.appendChild(rootElement);
            for(int i=0; i<listArray.size();i+=6)
                rootElement.appendChild(getList(doc, listArray.get(i), listArray.get(i+1), listArray.get(i+2),listArray.get(i+3), listArray.get(i+4), listArray.get(i+5)));
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File("./src/ru/bstu/it31/mishchenko/lab5/Список_учащихся_new.xml"));
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Node getList(Document doc, String name, String surname, String patronymic, String school, String classSchool, String rating) {
        Element list = doc.createElement("Список_учащихся");
        list.appendChild(getListElements(doc, list, "Имя", name));
        list.appendChild(getListElements(doc, list, "Фамилия", surname));
        list.appendChild(getListElements(doc, list, "Отчество", patronymic));
        list.appendChild(getListElements(doc, list, "Школа", school));
        list.appendChild(getListElements(doc, list, "Класс", classSchool));
        list.appendChild(getListElements(doc, list, "Средняя_оценка", rating));

        return list;
    }
    private static Node getListElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
    public static int checkFormat()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите форму представления:");
        System.out.println("XML(1)");
        System.out.println("DB(2)");
        Integer value= sc.nextInt();
            try {
                if (value==1){
                    return 1;

                }
                if (value==2)
                {
                    return 2;

                }
            } catch (Exception e){
                System.out.println("Упс! Шаловливые бесы опять что-то натворили...");
                return 3;
            }
            return 3;
    }
    public static void showList(int format, ArrayList<String> listArray)
    {
        if (format==1)
        {
            String filePath = "./src/ru/bstu/it31/mishchenko/lab5/Список_учащихся.xml";
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                DefaultHandler handler = new DefaultHandler() {
                    String tag = "";
                    int sum=0;

                    @Override

                    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                        if (qName.equalsIgnoreCase("Список_учащихся"))
                            System.out.println("\nЭлемент "+qName);
                        tag = qName;
                    }

                    @Override
                    public void characters(char ch[], int start, int length) throws SAXException {
                        if (tag.equalsIgnoreCase("Код"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Код: " + listArray.get(sum));
                            sum++;
                        }
                        else
                        if (tag.equalsIgnoreCase("Имя"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Имя: " + listArray.get(sum));
                            sum++;
                        }
                        else
                        if (tag.equalsIgnoreCase("Фамилия"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Фамилия: " + listArray.get(sum));
                            sum++;
                        }
                        else
                        if (tag.equalsIgnoreCase("Отчество"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Отчество: " + listArray.get(sum));
                            sum++;
                        }
                        else
                        if (tag.equalsIgnoreCase("Школа"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Школа: " + listArray.get(sum));
                            sum++;
                        }
                        else
                        if (tag.equalsIgnoreCase("Класс"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Класс: " + listArray.get(sum));
                            sum++;
                        }
                        else
                        if (tag.equalsIgnoreCase("Средняя_оценка"))
                        {
                            listArray.add(new String(ch, start, length));
                            System.out.println("Средняя оценка: " + listArray.get(sum));
                            sum++;
                        }
                    }

                    @Override
                    public void endElement(String uri,String localName,String qName) throws SAXException {
                        tag = "";
                    }
                };

                saxParser.parse(filePath, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void displayMenu(ArrayList<String> listArray, Scanner sc)
    {
        System.out.println("\nВыберите дальнейшее действие:");
        System.out.println("(1) Добавить запись;");
        System.out.println("(2) Редактировать запись;");
        System.out.println("(3) Удалить запись;");
        System.out.println("(4) Конвертация;");
        System.out.println("(5) Поиск;");
        System.out.println("(6) Выход из приложения.");
        Integer value= sc.nextInt();
        switch (value){
            case 1: addRecord(listArray, sc);
                break;
            case 2: changeRecord(listArray, sc);
                break;
            case 3: deleteRecord(listArray, sc);
                break;
            case 4: writeDOM(listArray, sc);
                break;
            case 5: findRecord(listArray, sc);
                break;
            case 6:
                break;
            default: System.out.println("Некорректное значение");
                break;
        }
    }
    public static void addRecord(ArrayList<String> listArray, Scanner sc)
    {
        System.out.println("Введите имя ученика: ");
        listArray.add(sc.next());
        System.out.println("Введите фамилию ученика: ");
        listArray.add(sc.next());
        System.out.println("Введите отчество ученика: ");
        listArray.add(sc.next());
        System.out.println("Введите номер школы: ");
        listArray.add(sc.next());
        System.out.println("Введите номер класса: ");
        listArray.add(sc.next());
        System.out.println("Введите среднюю оценку ученика: ");
        listArray.add(sc.next());
            System.out.println(listArray);
            displayMenu(listArray,sc);
    }
    public static void changeRecord(ArrayList<String> listArray, Scanner sc)
    {
        System.out.println("Введите фамилию ученика, информацию о котором необходимо изменить: ");
        String surname=sc.next();
        int index = listArray.indexOf(surname);

        if(index!=-1){
            String text="";
            String text1="";
            System.out.println("Изменить имя ученика? (да/нет)");
            String question=sc.next();

            if(question.equals("да")){
                text=listArray.get(index-1);
                System.out.println("Предыдущее имя= "+text);
                System.out.println("Новое имя= ");
                text1=sc.next();
                listArray.set(index-1, text1);
            }
            System.out.println("Изменить фамилию ученика? (да/нет)");
            question=sc.next();

            if(question.equals("да")){
                text=listArray.get(index);
                System.out.println("Предыдущая фамилия= "+text);
                System.out.println("Новая фамилия= ");
                text1=sc.next();
                listArray.set(index, text1);
            }
            System.out.println("Изменить отчество ученика? (да/нет)");
            question=sc.next();

            if(question.equals("да")){
                text=listArray.get(index+1);
                System.out.println("Предыдущая фамилия= "+text);
                System.out.println("Новая фамилия= ");
                text1=sc.next();
                listArray.set(index+1, text1);
            }
            System.out.println("Изменить номер школы ученика? (да/нет)");
            question=sc.next();

            if(question.equals("да")){
                text=listArray.get(index+2);
                System.out.println("Предыдущая школа= "+text);
                System.out.println("Новая школа= ");
                text1=sc.next();
                listArray.set(index+2, text1);
            }
            System.out.println("Изменить номер класса ученика? (да/нет)");
            question=sc.next();

            if(question.equals("да")){
                text=listArray.get(index+3);
                System.out.println("Предыдущий класс= "+text);
                System.out.println("Новый класс= ");
                text1=sc.next();
                listArray.set(index+3, text1);
            }
            System.out.println("Изменить средний балл ученика? (да/нет)");
            question=sc.next();

            if(question.equals("да")){
                text=listArray.get(index+4);
                System.out.println("Предыдущий балл= "+text);
                System.out.println("Новый балл= ");
                text1=sc.next();
                listArray.set(index+4, text1);
            }
            System.out.println(listArray);
            displayMenu(listArray,sc);
        }
        else System.out.println("Введённого вами ученика нет в БД");
    }
    public static void deleteRecord(ArrayList<String> listArray, Scanner sc)
    {
        System.out.println("Введите фамилию ученика, информацию о котором необходимо удалить: ");
        String Name=sc.next();
        int index = listArray.indexOf(Name);
        if(index!=-1){
            listArray.subList(index-1, index+5).clear();
            System.out.println(listArray);
            displayMenu(listArray,sc);
        }
        else System.out.println("Введённого вами ученика нет в БД");
    }
    public static void fChoose(ArrayList<String> listArray)
    {
        float min=10000000, max=0;
        int ind1=0, ind2=0;
        for(int i=0; i<listArray.size(); i+=6){
            if(min > Float.parseFloat(listArray.get(i+5).replace(',', '.'))){
                min=Float.parseFloat(listArray.get(i+5).replace(',', '.'));
                ind1=i+5;
            }
            if(max < Float.parseFloat(listArray.get(i+5).replace(',', '.'))){
                max=Float.parseFloat(listArray.get(i+5).replace(',', '.'));
                ind2=i+5;
            }
        }
        System.out.println("Имя ученика, имеющего высший балл: " + listArray.get(ind2-5)
                + "\nФамилия: " + listArray.get(ind2-4)
                + "\nОтчество: " + listArray.get(ind2-3)
                + "\nШкола: "+ listArray.get(ind2-2)
                + "\nКласс: " + listArray.get(ind2-1)
                +"\nСредний балл:" + listArray.get(ind2)+"\n");
        System.out.println("Имя ученика, имеющего низший балл: " + listArray.get(ind1-5)
                + "\nФамилия: " +listArray.get(ind1-4)
                + "\nОтчество:" + listArray.get(ind1-3)
                + "\nШкола:"+ listArray.get(ind1-2)
                + "\nКласс:"+ listArray.get(ind1-1)
                + "\nСредний балл:" + listArray.get(ind1)+"\n");
    }
    public static void tChoose(ArrayList<String> listArray, Scanner sc)
    {
        System.out.println("Введите фамилию ученика:");
        String c=sc.next();
        for(int i=0; i<listArray.size(); i+=6)
            if(c.equals(listArray.get(i+1)))
                System.out.println("Имя:" + listArray.get(i)
                        + "\nФамилия:" + listArray.get(i+1)
                        + "\nОтчество:" + listArray.get(i+2)
                        + "\nШкола:"+ listArray.get(i+3)
                        + "\nКласс:" + listArray.get(i+4)
                        + "\nСредний балл:" + listArray.get(i+5)+"\n" );
    }
    public static void findRecord(ArrayList<String> listArray, Scanner sc)
    {
        System.out.println("\nВыберите дальнейшее действие:");
        System.out.println("(1) Поиск наивысшего и низшего баллов;");
        System.out.println("(2) Поиск информации по фамилии ученика;");
        Integer value= sc.nextInt();
        switch (value){
            case 1: fChoose(listArray);
                break;
            case 2: tChoose(listArray, sc);
                break;
            default:System.out.println("Некорректное значение");
                break;
        }
    }
    public static void main(String[] args){
        ArrayList<String> listArray = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        int format=list.checkFormat();
        System.out.println(format);
        showList(format, listArray);
        displayMenu(listArray, sc);
    }
}
