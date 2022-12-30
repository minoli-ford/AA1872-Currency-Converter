from flask import Flask, render_template, request
import zeep


templatesLocation = 'H:\SLTC\AA1872_Currency Converter\Client\website\Templates'
app = Flask('CurrencyConverter', template_folder= templatesLocation)

wsdl = 'http://localhost:8888/CurrencyConversionWebServiceAA1872?wsdl'
client = zeep.Client(wsdl=wsdl)

output = ''

@app.route('/', methods=['GET'])
def home():
    currencyList = client.service.getCurrencyList()
    return render_template("index.html", currencyList = currencyList, len = len(currencyList))

@app.route("/", methods=['POST'])
def currencyConvert():
    currency01 = request.form['currencytype01']
    currency02 = request.form['currencytype02']
    amount = request.form['amount']

    output = str(client.service.convert(amount, currency01, currency02))
    currencyList = client.service.getCurrencyList()

    return render_template("index.html", currencyList = currencyList, len = len(currencyList), output = output)


if __name__ == "__main__":
    app.run(debug=True)