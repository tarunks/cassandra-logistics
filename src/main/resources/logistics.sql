-- CREATE KEYSPACE
-- One Keyspace named shipment_india_uk_us at each node of India, UK and US data center

CREATE KEYSPACE IF NOT EXISTS shipment_india_uk_us 
WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor' : 1 };

-- One Keyspace named shipment_uk_us at each node of UK and US data center.

CREATE KEYSPACE IF NOT EXISTS shipment_uk_us 
WITH REPLICATION = {'class': 'NetworkTopologyStrategy', 'uk' : 3, 'us' : 3 }

-- One Keyspace named shipment_india_uk at each node of UK and US data center.

CREATE KEYSPACE IF NOT EXISTS shipment_india_uk
WITH REPLICATION = {'class': 'NetworkTopologyStrategy', 'india' : 3, 'uk' : 3 }


-- One global Keyspace  named global_shipment_track at every data centers i.e. UK, US and India.

CREATE KEYSPACE IF NOT EXISTS global_shipment_track 
WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor' : 1 };

-- Create one  User defined Type for customer address 

use shipment_india_uk_us;

CREATE TYPE IF NOT EXISTS ADDRESS (
name text,
street text,
city text,
state text,
country text,
zip text, 
phone list<text>, 
email text 
);

CREATE TABLE IF NOT EXISTS shipment_detail (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
Actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY ( (source_zone_id, destination_zone_id), waybill_no)
);

CREATE TABLE IF NOT EXISTS shipment_detail_source_location (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
Actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY ( source_zone_id, waybill_no)
);

CREATE TABLE IF NOT EXISTS shipment_detail_destination_location (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY (destination_zone_id, waybill_no)
);

CREATE TABLE shipment_detail_by_waybillno (
    source_zone_id text,
    destination_zone_id text,
    waybill_no uuid,
    actual_delivery_date timestamp,
    current_status text,
    destination_address frozen<address>,
    dispatch_date timestamp,
    exp_delivery_date timestamp,
    is_damaged boolean,
    is_returned boolean,
    item_name text,
    package_type text,
    priority text,
    remarks text,
    source_address frozen<address>,
    PRIMARY KEY (waybill_no)
);

-- ********************************
use shipment_uk_us;

CREATE TYPE IF NOT EXISTS ADDRESS (
name text,
street text,
city text,
state text,
country text,
zip text, 
phone list<text>, 
email text 
);

CREATE TABLE IF NOT EXISTS shipment_detail (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
Actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY ( (source_zone_id, destination_zone_id), waybill_no)
);

CREATE TABLE IF NOT EXISTS shipment_detail_source_location (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
Actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY ( source_zone_id, waybill_no)
);

CREATE TABLE IF NOT EXISTS shipment_detail_destination_location (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY (destination_zone_id, waybill_no)
);

CREATE TABLE shipment_detail_by_waybillno (
    source_zone_id text,
    destination_zone_id text,
    waybill_no uuid,
    actual_delivery_date timestamp,
    current_status text,
    destination_address frozen<address>,
    dispatch_date timestamp,
    exp_delivery_date timestamp,
    is_damaged boolean,
    is_returned boolean,
    item_name text,
    package_type text,
    priority text,
    remarks text,
    source_address frozen<address>,
    PRIMARY KEY (waybill_no)
);


-- ******************************************************

use shipment_india_uk ;

CREATE TYPE IF NOT EXISTS ADDRESS (
name text,
street text,
city text,
state text,
country text,
zip text, 
phone list<text>, 
email text 
);

CREATE TABLE IF NOT EXISTS shipment_detail (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
Actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY ( (source_zone_id, destination_zone_id), waybill_no)
);

CREATE TABLE IF NOT EXISTS shipment_detail_source_location (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
Actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY ( source_zone_id, waybill_no)
);

CREATE TABLE IF NOT EXISTS shipment_detail_destination_location (
Source_zone_id  TEXT,
Destination_zone_id TEXT,
Waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
package_type TEXT,
priority TEXT,
dispatch_date TIMESTAMP,
exp_delivery_date TIMESTAMP,
actual_delivery_date TIMESTAMP,
current_status TEXT,
item_name TEXT,
Is_returned BOOLEAN,
Is_damaged BOOLEAN,
Remarks TEXT,
PRIMARY KEY (destination_zone_id, waybill_no)
);

CREATE TABLE shipment_detail_by_waybillno (
    source_zone_id text,
    destination_zone_id text,
    waybill_no uuid,
    actual_delivery_date timestamp,
    current_status text,
    destination_address frozen<address>,
    dispatch_date timestamp,
    exp_delivery_date timestamp,
    is_damaged boolean,
    is_returned boolean,
    item_name text,
    package_type text,
    priority text,
    remarks text,
    source_address frozen<address>,
    PRIMARY KEY (waybill_no)
);

-- **********************************88---

use global_shipment_track;
CREATE TYPE IF NOT EXISTS ADDRESS (
name text,
street text,
city text,
state text,
country text,
zip text, 
phone list<text>, 
email text 
);


CREATE TYPE IF NOT EXISTS TRACK(
location text,
zone text,
update_date TIMESTAMP,
remarks text
);

CREATE TABLE IF NOT EXISTS shipment_track (
waybill_no UUID,
source_address frozen <ADDRESS>,
destination_address frozen <ADDRESS>,
route_path List<frozen<TRACK>>,
PRIMARY KEY (waybill_no)
);

