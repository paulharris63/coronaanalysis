## API:
https://www.cbs.nl/nl-nl/onze-diensten/open-data/statline-als-open-data

e.g. Population:

https://opendata.cbs.nl/ODataApi/odata/37296ned/UntypedDataSet?$select=Perioden,+TotaleBevolking_1,+Mannen_2,+Vrouwen_3

https://opendata.cbs.nl/ODataApi/odata/37296ned/

`{
  "odata.metadata":"https://opendata.cbs.nl/ODataApi/OData/37296ned/$metadata","value":[
    {
      "name":"TableInfos","url":"https://opendata.cbs.nl/ODataApi/odata/37296ned/TableInfos"
    },{
      "name":"UntypedDataSet","url":"https://opendata.cbs.nl/ODataApi/odata/37296ned/UntypedDataSet"
    },{
      "name":"TypedDataSet","url":"https://opendata.cbs.nl/ODataApi/odata/37296ned/TypedDataSet"
    },{
      "name":"DataProperties","url":"https://opendata.cbs.nl/ODataApi/odata/37296ned/DataProperties"
    },{
      "name":"CategoryGroups","url":"https://opendata.cbs.nl/ODataApi/odata/37296ned/CategoryGroups"
    },{
      "name":"Perioden","url":"https://opendata.cbs.nl/ODataApi/odata/37296ned/Perioden"
    }
  ]
}`

e.g. Registered deaths:

https://opendata.cbs.nl/ODataApi/odata/70895ned

`{
  "odata.metadata":"https://opendata.cbs.nl/ODataApi/OData/70895ned/$metadata","value":[
    {
      "name":"TableInfos","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/TableInfos"
    },{
      "name":"UntypedDataSet","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/UntypedDataSet"
    },{
      "name":"TypedDataSet","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/TypedDataSet"
    },{
      "name":"DataProperties","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/DataProperties"
    },{
      "name":"CategoryGroups","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/CategoryGroups"
    },{
      "name":"Geslacht","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/Geslacht"
    },{
      "name":"LeeftijdOp31December","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/LeeftijdOp31December"
    },{
      "name":"Perioden","url":"https://opendata.cbs.nl/ODataApi/odata/70895ned/Perioden"
    }
  ]
}`

https://opendata.cbs.nl/ODataApi/odata/70895ned/UntypedDataSet?$filter=substring(Perioden,0,4) ge '2010' and substring(Perioden,4,2) eq 'JJ'

https://opendata.cbs.nl/ODataApi/odata/70895ned/UntypedDataSet?$select=Overledenen_1,Perioden&$filter=substring(Perioden, 4,1) eq 'W' and Geslacht eq '1100' and LeeftijdOp31December eq '10000'

Geslacht = 1100(both), 3000(male), 4000(female)

LeeftijdOp31December = 10000(all) , 41700, 53950, 21700

file:///C:/Users/paul/Downloads/handleiding-cbs-open-data-services.pdf
