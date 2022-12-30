package com.sltc.aa1872;

import org.json.simple.parser.ParseException;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class ConverterWebService
{
    @WebMethod
    public double convert(double amountInSourceCurrency, String sourceCurrency, String targetCurrency) throws IOException, ParseException {

        ReadRates readRates = new ReadRates();

        Double sourceCurrencyRate = Double.valueOf(readRates.conversionRates.get(sourceCurrency).toString());
        Double targetCurrencyRate = Double.valueOf(readRates.conversionRates.get(targetCurrency).toString());

        return (amountInSourceCurrency / sourceCurrencyRate) * targetCurrencyRate;
    }

    @WebMethod
    public ArrayList getCurrencyList() throws IOException, ParseException {

        ReadRates readRates = new ReadRates();
        ArrayList currencyList = readRates.currencyList;
        Collections.sort(currencyList);
        return currencyList;
    }

    public static void main(String[] args){

        Endpoint.publish("http://localhost:8888/CurrencyConversionWebServiceAA1872", new ConverterWebService());
        System.out.println("Server Started Successfully.........");
    }
}
