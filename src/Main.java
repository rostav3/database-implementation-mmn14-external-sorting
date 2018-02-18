import data.DataLoader;
import data.Page;
import data.Record;
import logics.ExternalSort;
import logics.PartsPartition;
import ui.UserConversation;
import utils.GlobalContexts;

import java.util.ArrayList;

/*************************************************************************************************
 * The main program sorted file by pages, when the pages size and count get by the user.
 * After sort the data, separate to pages and save to the csv.
 * Created by Stav Rockah on 1/21/2018.
 ************************************************************************************************/
public class Main {
    public static void main(String [] args) {
        // Get inputs from the user
        UserConversation userConversation = new UserConversation();
        Integer pageSize = userConversation.getPageSize();
        Integer pageNumber = userConversation.getPageNumber();
        String csv = userConversation.getDataPath();

        // Load the csv data
        DataLoader dataLoader = new DataLoader();
        ArrayList<Record> data = dataLoader.loadCsvData(csv);

        // Split to pages by user inputs
        GlobalContexts.partsPartition = new PartsPartition(pageSize);
        ArrayList<Page> pages =  GlobalContexts.partsPartition.getDataByPages(data, true);

        // Sot the data
        ExternalSort externalSort = new ExternalSort(pages, pageNumber);

        // Split to sort data by user inputs
//        System.out.println("Split the sort list to pages..");
//        pages =  partsPartition.getDataByPages(externalSort.getRecords(), false);


        // save the sorted data
        String recordsToSave = "";
        for (int i = 0; i < pages.size(); i++){
            recordsToSave += pages.get(i).toString();
            if (i !=pages.size() - 1){
                recordsToSave += "\n";
            }
        }
        csv = csv.substring(0,csv.indexOf(".")) + "_sort.csv";
        dataLoader.saveDataToCsv(recordsToSave, csv);
    }
}
