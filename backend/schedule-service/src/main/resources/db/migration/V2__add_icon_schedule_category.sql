ALTER TABLE schedule_category ADD COLUMN icon VARCHAR(255) AFTER title;
ALTER TABLE ended_schedule add column schedule_category_icon varchar(255) after schedule_category_title;

update ended_schedule set schedule_category_icon = case schedule_category_title
    when "목욕" then "play"
    when "사료 구입" then "shopping"
    when "산책" then "play"
    when "사냥 놀이" then "play"
end;

UPDATE schedule_category SET icon = 'play' WHERE (schedule_category_id = 1);
UPDATE schedule_category SET icon = 'play' WHERE (schedule_category_id = 2);
UPDATE schedule_category SET icon = 'shopping' WHERE (schedule_category_id = 3);
UPDATE schedule_category SET icon = 'play' WHERE (schedule_category_id = 4);
