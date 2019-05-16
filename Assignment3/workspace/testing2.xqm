(:module namespace gpd='http://www.example.org/xquery/clinic/getProviderInfo' :)

import schema namespace p="http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace t="http://www.example.org/schemas/clinic/Treatment" at "Treament.xsd";
import schema namespace c="http://www.example.org/clinic" at "Clinic.xsd";
declare namespace d="http://www.example.org/schemas/clinic/drug";
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
let $clinic :=doc("ClinicData.xml")/c:Clinic
for $patient in doc("ClinicData.xml")/c:Clinic/p:patients
where $patient/p:PatientId/text()=23456781
 return <d:drug>{local:getPatientDrugs($patient/p:PatientId,$clinic)}</d:drug>