/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mana.report;

import com.crystaldecisions.reports.reportdefinition.ReportExportOptions;
import com.crystaldecisions.reports.reportdefinition.ReportOptions;
import com.crystaldecisions.reports.reportengineinterface.JPEReportSourceFactory;
import com.crystaldecisions.sdk.occa.report.application.ParameterFieldController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfos;
import com.crystaldecisions.sdk.occa.report.data.ConstantValue;
import com.crystaldecisions.sdk.occa.report.data.Field;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.IParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldValue;
import com.crystaldecisions.sdk.occa.report.data.Value;
import com.crystaldecisions.sdk.occa.report.data.Values;
import com.crystaldecisions.sdk.occa.report.exportoptions.DataOnlyExcelExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.PDFExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.PropertyBag;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSource;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSourceFactory2;
import java.util.Map; 
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.spi.LoggerFactory;

/**
 *
 * @author mana
 */
@ManagedBean(name="report")
@RequestScoped
public class Report {
    
    IReportSource reportSource;
    
    ConnectionInfos connInfos;
    
    @PostConstruct
    public void init()
    {
        try {
            Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String report = params.get("Report_name");

            //IReportSourceFactory2 rptSrcFactory = new JPEReportSourceFactory();
            ReportClientDocument doc=new ReportClientDocument();
            doc.open(report, 0);
     
            String connString = "jdbc:oracle:thin:@localhost:1521:example";
            PropertyBag bag = new PropertyBag();
            bag.put("Connection URL", connString);
            bag.put("Server Name", "localhost");
            bag.put("Database Name", "example");
            bag.put("Server Type", "JDBC (JNDI)");
            bag.put("Database DLL", "crdb_jdbc.dll");
            bag.put("Database Class Name", "oracle.jdbc.driver.OracleDriver");
            connInfos= new ConnectionInfos();
            IConnectionInfo connInfo1 = new ConnectionInfo();
            connInfo1.setUserName("admin");
            connInfo1.setPassword("admin");          
            connInfo1.setAttributes(bag);
            connInfos.add(connInfo1);
            IConnectionInfo icon=(doc.getDatabaseController().getConnectionInfos(null).size()>0)?doc.getDatabaseController().getConnectionInfos(null).get(0):new ConnectionInfo() ;
            doc.getDatabaseController().replaceConnection(icon, connInfo1, null, 0);
            
            Set<String> keys=params.keySet();
            for(String key:keys)
            {
                if(!key.contentEquals("Report_name")){
                    System.err.println( "Key: "+key+"  *** Value: "+params.get(key));
                    doc.getDataDefController().getParameterFieldController().setCurrentValue("",key,params.get(key));
                }
            }
            
            
            
            
            reportSource =doc.getReportSource(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }

    public ConnectionInfos getConnInfos() {
        return connInfos;
    }

    public void setConnInfos(ConnectionInfos connInfos) {
        this.connInfos = connInfos;
    }

    
    
    public IReportSource getReportSource() {
        return reportSource;
    }

    public void setReportSource(IReportSource reportSource) {
        this.reportSource = reportSource;
    }
    
    
    
}
