--liquibase formatted sql
--changeset view:1 runAlways:true runOnChange:true splitStatements:false stripComments:false dbms:postgresql

do
$$
declare
    view_list text;
begin
    select into view_list string_agg(table_name, ', ')
    from information_schema.views
    where table_schema = current_schema;
    if view_list is not null then
        execute 'drop view if exists ' || view_list;
    end if;
end;
$$