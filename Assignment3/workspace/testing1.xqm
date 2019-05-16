
import schema namespace p="http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace t="http://www.example.org/schemas/clinic/Treatment" at "Treament.xsd";
import schema namespace c="http://www.example.org/clinic" at "Clinic.xsd";
declare namespace ps="http://www.example.org/schemas/clinic/patients";

declare  function local:getPatientTreatments ($pid as xs:integer,$database as element(c:Clinic)) as item ()*
{
for $treatment in $database/p:patients
where $treatment/p:PatientId=$pid
return  
<ps:patients>
<ps:pid>{$treatment/p:PatientId/text()}</ps:pid>
<ps:treatments>{$treatment/p:treatments}</ps:treatments>
</ps:patients> 
};
let $clinic :=doc("ClinicData.xml")/c:Clinic
for $patient in doc("ClinicData.xml")/c:Clinic/p:patients
where $patient/p:PatientId/text()=23456781

 return <ps:patients>{local:getPatientTreatments($patient/p:PatientId,$clinic)}</ps:patients>
