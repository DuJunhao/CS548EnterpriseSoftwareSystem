import schema namespace p="http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace t="http://www.example.org/schemas/clinic/Treatment" at "Treament.xsd";
import schema namespace pr="http://www.example.org/schemas/clinic/provider" at "Provider.xsd";
import schema namespace c="http://www.example.org/clinic" at "Clinic.xsd";

declare namespace ps="http://www.example.org/schemas/clinic/patients";
declare namespace d="http://www.example.org/schemas/clinic/drug";
declare namespace ti="http://www.example.org/schemas/clinic/treatmentInfo";
declare namespace ph="http://www.example.org/schemas/clinic/providerHistory";
declare namespace di="http://www.example.org/schemas/clinic/drugInfo";

import module namespace gpt="http://www.example.org/xquery/clinic/getPatientTreatments" at "testing1.xqm" ;
import module namespace gpd="http://www.example.org/xquery/clinic/getPatientDrugs" at "testing2.xqm";
import module namespace gti="http://www.example.org/xquery/clinic/getTreatmentInfo" at "testing3.xqm";
import module namespace gpi="http://www.example.org/xquery/clinic/getProviderInfo" at "testing4.xqm";
import module namespace gdi="http://www.example.org/xquery/clinic/getDrugInfo" at "testing5.xqm";

declare function local:getPatientTreatments ($pid as xs:integer,$database as element(c:Clinic)) as item ()*
{
for $treatment in $database/p:patients
where $treatment/p:PatientId=$pid
return  
<ps:patients>
<ps:pid>{$treatment/p:PatientId/text()}</ps:pid>
<ps:treatments>{$treatment/p:treatments}</ps:treatments>
</ps:patients> 
};
declare function local:getPatientDrugs($patient-id as xs:integer,
$database as element(c:Clinic))
as element(d:drug)*
{
    for $patient in $database/p:patients
    where $patient/p:PatientId = $patient-id
    for $treatment in $patient/p:treatments/p:treatment
    where $treatment//t:DrugTreatment
    return
        <d:drug>
            <d:name>{$treatment/t:DrugTreatment/t:name/text()}</d:name>
            <d:dosage>{$treatment/t:DrugTreatment/t:dosage/text()}</d:dosage>
            <d:diagnosis>{$treatment/t:diagnosis/text()}</d:diagnosis>
        </d:drug>
};
declare function local:getTreatmentInfo($database as element(c:Clinic))
as item()*
{
    for $patient in $database/p:patients
    
    for $treatment in $patient/p:treatments/p:treatment/node()
    return 
    typeswitch($treatment)
    case $Drug as element(t:DrugTreatment)
    return<ti:treatmentInfo>
            <ti:name>DrugTreatment</ti:name>
            <ti:pid>{$patient/p:PatientId/text()}</ti:pid>
            <ti:ProviderId>{$treatment/../t:ProviderId/text()}</ti:ProviderId>
        </ti:treatmentInfo>
        
    case $Surgery as element(t:Surgery)
    return<ti:treatmentInfo>
            <ti:name>Surgery</ti:name>
            <ti:pid>{$patient/p:PatientId/text()}</ti:pid>
            <ti:ProviderId>{$treatment/../t:ProviderId/text()}</ti:ProviderId>
        </ti:treatmentInfo>
    case $Radiology as element(t:Radiology)
    return<ti:treatmentInfo>
            <ti:name>Radiology</ti:name>
            <ti:pid>{$patient/p:PatientId/text()}</ti:pid>
            <ti:ProviderId>{$treatment/../t:ProviderId/text()}</ti:ProviderId>
        </ti:treatmentInfo>
    default
    return ""
};
declare function local:getProviderInfo($database as element(c:Clinic))
as item()*
{
    for $provider in $database/pr:providers
    where $provider/pr:ProviderId
    for $treatment in $database/p:patients/p:treatments/p:treatment/node()
    where $treatment/../t:ProviderId=$provider/pr:ProviderId
    return typeswitch($treatment)
    case $Drug as element(t:DrugTreatment)
    return
    <ph:providerHistory> 
    <ph:providerId>{$provider/pr:ProviderId/text()}</ph:providerId>
    <ph:providerName>{$provider/pr:name/text()}</ph:providerName>
    <ph:pid>{$treatment/../../../p:PatientId/text()}</ph:pid>
    <ph:treatment>drugTreatment</ph:treatment>
    </ph:providerHistory>
    case $Surgery as element(t:Surgery)
    return
    <ph:providerHistory> 
    <ph:providerId>{$provider/pr:ProviderId/text()}</ph:providerId>
    <ph:providerName>{$provider/pr:name/text()}</ph:providerName>
    <ph:pid>{$treatment/../../../p:PatientId/text()}</ph:pid>
    <ph:treatment>surgery</ph:treatment>
    </ph:providerHistory>
    case $Radiology as element(t:Radiology )
    return
    <ph:providerHistory> 
    <ph:providerId>{$provider/pr:ProviderId/text()}</ph:providerId>
    <ph:providerName>{$provider/pr:name/text()}</ph:providerName>
    <ph:pid>{$treatment/../../../p:PatientId/text()}</ph:pid>
    <ph:treatment>radiology</ph:treatment>
    </ph:providerHistory>
    default
    return ""
};
declare function local:getDrugInfo($database as element(c:Clinic))
as item()*
{
    for $patient in $database/p:patients
    for $treatment in $patient/p:treatments/p:treatment
    where $treatment//t:DrugTreatment
    return
        <di:drugInfo>
            <di:patients>{$treatment/../../p:PatientId/text()}</di:patients>
            <di:name>{$treatment/t:DrugTreatment/t:name/text()}</di:name>
            <di:diagnosis>{$treatment/t:diagnosis/text()}</di:diagnosis>
            <di:dosage>{$treatment/t:DrugTreatment/t:dosage/text()}</di:dosage>
            <di:physician>{$treatment/t:DrugTreatment/t:physician/text()}</di:physician>
        </di:drugInfo>
};
let $clinic :=doc("ClinicData.xml")/c:Clinic
for $patient in doc("ClinicData.xml")/c:Clinic/p:patients
where $patient/p:PatientId/text()=23456781

 return <ps:patients>{gpt:getPatientTreatments($patient/p:PatientId,$clinic)}</ps:patients>