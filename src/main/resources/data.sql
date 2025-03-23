-- Zones
INSERT INTO zones (zone_code, zone_name, region, headquarters, description, active, created_date)
SELECT 'CR', 'Central Railway', 'CENTRAL', 'Mumbai CST', 'Central zone covering Maharashtra and parts of MP', true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM zones WHERE zone_code = 'CR');

INSERT INTO zones (zone_code, zone_name, region, headquarters, description, active, created_date)
SELECT 'WR', 'Western Railway', 'WESTERN', 'Mumbai Central', 'Western zone covering Gujarat and parts of Rajasthan', true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM zones WHERE zone_code = 'WR');

INSERT INTO zones (zone_code, zone_name, region, headquarters, description, active, created_date)
SELECT 'NR', 'Northern Railway', 'NORTHERN', 'New Delhi', 'Northern zone covering Delhi, Punjab, and UP', true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM zones WHERE zone_code = 'NR');

INSERT INTO zones (zone_code, zone_name, region, headquarters, description, active, created_date)
SELECT 'SR', 'Southern Railway', 'SOUTHERN', 'Chennai', 'Southern zone covering Tamil Nadu and Kerala', true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM zones WHERE zone_code = 'SR');

INSERT INTO zones (zone_code, zone_name, region, headquarters, description, active, created_date)
SELECT 'ER', 'Eastern Railway', 'EASTERN', 'Kolkata', 'Eastern zone covering West Bengal and Bihar', true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM zones WHERE zone_code = 'ER');

-- Stations
INSERT INTO stations (
    station_code, station_name, address, number_of_platforms, contact_number,
    email, city, state, latitude, longitude, has_food_plaza, has_wifi, 
    has_retiring_room, has_waiting_room, zone_id, active, created_date
) 
SELECT 
    'CSMT', 'Mumbai CST', 'Fort, Mumbai-400001', 18, '022-22621645', 
    'csmt@railways.com', 'Mumbai', 'Maharashtra', 18.9398, 72.8354, true, true, true, true,
    z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'CR'
AND NOT EXISTS (SELECT 1 FROM stations WHERE station_code = 'CSMT');

INSERT INTO stations (
    station_code, station_name, address, number_of_platforms, contact_number,
    email, city, state, latitude, longitude, has_food_plaza, has_wifi, 
    has_retiring_room, has_waiting_room, zone_id, active, created_date
) 
SELECT 
    'BCT', 'Mumbai Central', 'Mumbai Central, Mumbai-400008', 6, '022-23077292', 
    'bct@railways.com', 'Mumbai', 'Maharashtra', 18.9692, 72.8193, true, true, true, true,
    z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'WR'
AND NOT EXISTS (SELECT 1 FROM stations WHERE station_code = 'BCT');

INSERT INTO stations (
    station_code, station_name, address, number_of_platforms, contact_number,
    email, city, state, latitude, longitude, has_food_plaza, has_wifi, 
    has_retiring_room, has_waiting_room, zone_id, active, created_date
) 
SELECT 
    'NDLS', 'New Delhi', 'Paharganj, New Delhi-110055', 16, '011-23234892', 
    'ndls@railways.com', 'New Delhi', 'Delhi', 28.6419, 77.2194, true, true, true, true,
    z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'NR'
AND NOT EXISTS (SELECT 1 FROM stations WHERE station_code = 'NDLS');

INSERT INTO stations (
    station_code, station_name, address, number_of_platforms, contact_number,
    email, city, state, latitude, longitude, has_food_plaza, has_wifi, 
    has_retiring_room, has_waiting_room, zone_id, active, created_date
) 
SELECT 
    'MAS', 'Chennai Central', 'Park Town, Chennai-600003', 12, '044-25357398', 
    'mas@railways.com', 'Chennai', 'Tamil Nadu', 13.0827, 80.2707, true, true, true, true,
    z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'SR'
AND NOT EXISTS (SELECT 1 FROM stations WHERE station_code = 'MAS');

INSERT INTO stations (
    station_code, station_name, address, number_of_platforms, contact_number,
    email, city, state, latitude, longitude, has_food_plaza, has_wifi, 
    has_retiring_room, has_waiting_room, zone_id, active, created_date
) 
SELECT 
    'HWH', 'Howrah Junction', 'Howrah-711101', 23, '033-26382358', 
    'hwh@railways.com', 'Howrah', 'West Bengal', 22.5843, 88.3403, true, true, true, true,
    z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'ER'
AND NOT EXISTS (SELECT 1 FROM stations WHERE station_code = 'HWH');

-- Classes
INSERT INTO classes (
    class_code, class_name, description, is_ac, has_wifi, has_pantry,
    has_charging_point, has_reading_light, active, created_date
) 
SELECT
    '1A', 'First AC', 'First Class Air Conditioned', true, true, true, true, true, true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM classes WHERE class_code = '1A');

INSERT INTO classes (
    class_code, class_name, description, is_ac, has_wifi, has_pantry,
    has_charging_point, has_reading_light, active, created_date
) 
SELECT
    '2A', 'Second AC', 'Two Tier Air Conditioned', true, true, true, true, true, true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM classes WHERE class_code = '2A');

INSERT INTO classes (
    class_code, class_name, description, is_ac, has_wifi, has_pantry,
    has_charging_point, has_reading_light, active, created_date
) 
SELECT
    '3A', 'Third AC', 'Three Tier Air Conditioned', true, true, true, true, true, true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM classes WHERE class_code = '3A');

INSERT INTO classes (
    class_code, class_name, description, is_ac, has_wifi, has_pantry,
    has_charging_point, has_reading_light, active, created_date
) 
SELECT
    'SL', 'Sleeper', 'Non-AC Sleeper Class', false, false, false, true, false, true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM classes WHERE class_code = 'SL');

INSERT INTO classes (
    class_code, class_name, description, is_ac, has_wifi, has_pantry,
    has_charging_point, has_reading_light, active, created_date
) 
SELECT
    'CC', 'Chair Car', 'Air Conditioned Chair Car', true, true, true, true, true, true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM classes WHERE class_code = 'CC');

INSERT INTO classes (
    class_code, class_name, description, is_ac, has_wifi, has_pantry,
    has_charging_point, has_reading_light, active, created_date
) 
SELECT
    '2S', 'Second Sitting', 'Non-AC Chair Car', false, false, false, true, false, true, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM classes WHERE class_code = '2S');

-- Trains
INSERT INTO trains (
    number, name, type, start_time, end_time, total_distance,
    average_speed, has_pantry_car, has_wifi, zone_id, active, created_date
) 
SELECT 
    12951, 'Rajdhani Express', 'RAJDHANI', '16:00'::time, '08:00'::time, 1384, 
    110, true, true, z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'CR'
AND NOT EXISTS (SELECT 1 FROM trains WHERE number = 12951);

INSERT INTO trains (
    number, name, type, start_time, end_time, total_distance,
    average_speed, has_pantry_car, has_wifi, zone_id, active, created_date
) 
SELECT 
    12269, 'Duronto Express', 'DURONTO', '22:00'::time, '18:00'::time, 2014, 
    95, true, true, z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'WR'
AND NOT EXISTS (SELECT 1 FROM trains WHERE number = 12269);

INSERT INTO trains (
    number, name, type, start_time, end_time, total_distance,
    average_speed, has_pantry_car, has_wifi, zone_id, active, created_date
) 
SELECT 
    12259, 'Shatabdi Express', 'SHATABDI', '06:00'::time, '21:00'::time, 2175, 
    120, true, true, z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'NR'
AND NOT EXISTS (SELECT 1 FROM trains WHERE number = 12259);

INSERT INTO trains (
    number, name, type, start_time, end_time, total_distance,
    average_speed, has_pantry_car, has_wifi, zone_id, active, created_date
) 
SELECT 
    12839, 'Chennai Mail', 'MAIL', '23:00'::time, '21:00'::time, 1659, 
    80, false, false, z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'SR'
AND NOT EXISTS (SELECT 1 FROM trains WHERE number = 12839);

INSERT INTO trains (
    number, name, type, start_time, end_time, total_distance,
    average_speed, has_pantry_car, has_wifi, zone_id, active, created_date
) 
SELECT 
    12123, 'Deccan Queen', 'SUPERFAST', '17:00'::time, '17:45'::time, 192, 
    90, true, true, z.id, true, CURRENT_TIMESTAMP
FROM zones z WHERE z.zone_code = 'CR'
AND NOT EXISTS (SELECT 1 FROM trains WHERE number = 12123);

-- Routes for specific trains
-- Routes for train 12951 (Mumbai CST to New Delhi)
INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12951),
    (SELECT id FROM stations WHERE station_code = 'CSMT'),
    0, NULL, '16:00'::time, 1, '1'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12951)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'CSMT')
    AND sequence = 1
);

INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12951),
    (SELECT id FROM stations WHERE station_code = 'BCT'),
    7, '16:30'::time, '16:35'::time, 2, '2'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12951)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'BCT')
    AND sequence = 2
);

INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12951),
    (SELECT id FROM stations WHERE station_code = 'NDLS'),
    1384, '08:00'::time, NULL, 3, '3'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12951)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'NDLS')
    AND sequence = 3
);

-- Routes for train 12269 (Mumbai Central to Howrah)
INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12269),
    (SELECT id FROM stations WHERE station_code = 'BCT'),
    0, NULL, '22:00'::time, 1, '1'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12269)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'BCT')
    AND sequence = 1
);

INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12269),
    (SELECT id FROM stations WHERE station_code = 'HWH'),
    2014, '18:00'::time, NULL, 2, '4'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12269)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'HWH')
    AND sequence = 2
);

-- Routes for train 12259 (New Delhi to Chennai)
INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12259),
    (SELECT id FROM stations WHERE station_code = 'NDLS'),
    0, NULL, '06:00'::time, 1, '5'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12259)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'NDLS')
    AND sequence = 1
);

INSERT INTO routes (train_id, station_id, distance_from_origin, arrival_time, departure_time, sequence, platform_number)
SELECT 
    (SELECT id FROM trains WHERE number = 12259),
    (SELECT id FROM stations WHERE station_code = 'MAS'),
    2175, '21:00'::time, NULL, 2, '2'
WHERE NOT EXISTS (
    SELECT 1 FROM routes 
    WHERE train_id = (SELECT id FROM trains WHERE number = 12259)
    AND station_id = (SELECT id FROM stations WHERE station_code = 'MAS')
    AND sequence = 2
);

-- Train Fares
INSERT INTO train_fares (
    train_id, class_id, from_station, to_station, distance_in_km,
    base_fare, dynamic_fare_multiplier, dynamic_pricing_enabled
)
SELECT 
    t.id,
    c.id,
    s1.station_code as from_station,
    s2.station_code as to_station,
    ABS(r2.distance_from_origin - r1.distance_from_origin) as distance_in_km,
    CASE 
        WHEN c.class_code = '1A' THEN 4800
        WHEN c.class_code = '2A' THEN 3800
        WHEN c.class_code = '3A' THEN 2800
        ELSE 1800
    END as base_fare,
    1.2 as dynamic_fare_multiplier,
    true as dynamic_pricing_enabled
FROM trains t
CROSS JOIN classes c
JOIN routes r1 ON r1.train_id = t.id
JOIN routes r2 ON r2.train_id = t.id
JOIN stations s1 ON s1.id = r1.station_id
JOIN stations s2 ON s2.id = r2.station_id
WHERE r1.sequence < r2.sequence
AND t.type IN ('RAJDHANI', 'SHATABDI', 'DURONTO')
AND c.class_code IN ('1A', '2A', '3A')
AND NOT EXISTS (
    SELECT 1 FROM train_fares tf 
    WHERE tf.train_id = t.id 
    AND tf.class_id = c.id 
    AND tf.from_station = s1.station_code 
    AND tf.to_station = s2.station_code
);

-- Coaches
INSERT INTO coaches (
    coach_number, train_id, class_id, sequence, total_seats, 
    has_pantry, has_wifi, has_charging_point, has_reading_light,
    seat_capacity, total_rows, seats_per_row, has_side_berths,
    side_berth_count, berth_configuration, is_ac, maintenance_due_date
)
SELECT 
    CONCAT(c.class_code, LPAD(ROW_NUMBER() OVER (PARTITION BY t.id, c.id ORDER BY c.class_code)::text, 2, '0')) as coach_number,
    t.id,
    c.id,
    ROW_NUMBER() OVER (PARTITION BY t.id ORDER BY c.class_code) as sequence,
    CASE 
        WHEN c.class_code = '1A' THEN 24
        WHEN c.class_code = '2A' THEN 48
        WHEN c.class_code = '3A' THEN 72
        ELSE 90
    END as total_seats,
    c.has_pantry,
    c.has_wifi,
    c.has_charging_point,
    c.has_reading_light,
    CASE 
        WHEN c.class_code = '1A' THEN 24
        WHEN c.class_code = '2A' THEN 48
        WHEN c.class_code = '3A' THEN 72
        ELSE 90
    END as seat_capacity,
    CASE 
        WHEN c.class_code = '1A' THEN 4
        WHEN c.class_code = '2A' THEN 6
        WHEN c.class_code = '3A' THEN 8
        ELSE 10
    END as total_rows,
    CASE 
        WHEN c.class_code = '1A' THEN 6
        WHEN c.class_code = '2A' THEN 8
        WHEN c.class_code = '3A' THEN 9
        ELSE 9
    END as seats_per_row,
    CASE 
        WHEN c.class_code IN ('1A', '2A', '3A') THEN true
        ELSE false
    END as has_side_berths,
    CASE 
        WHEN c.class_code = '1A' THEN 2
        WHEN c.class_code = '2A' THEN 4
        WHEN c.class_code = '3A' THEN 6
        ELSE 0
    END as side_berth_count,
    CASE 
        WHEN c.class_code = '1A' THEN '{"layout": "2+2", "side_berths": 2}'
        WHEN c.class_code = '2A' THEN '{"layout": "3+3", "side_berths": 4}'
        WHEN c.class_code = '3A' THEN '{"layout": "3+3", "side_berths": 6}'
        ELSE '{"layout": "3+3", "side_berths": 0}'
    END::jsonb as berth_configuration,
    c.is_ac,
    CURRENT_DATE + INTERVAL '180 days' as maintenance_due_date
FROM trains t
CROSS JOIN classes c
WHERE t.type IN ('RAJDHANI', 'SHATABDI', 'DURONTO')
AND c.class_code IN ('1A', '2A', '3A')
AND NOT EXISTS (
    SELECT 1 FROM coaches co 
    WHERE co.train_id = t.id 
    AND co.class_id = c.id
);

-- Initial Seat Availability (for next 30 days)
INSERT INTO seat_availabilities (
    train_id, class_id, travel_date, available_seats, waiting_list, 
    total_seats, rac_seats, rac_available
)
SELECT 
    t.id,
    c.id,
    CURRENT_DATE + (i || ' days')::interval as travel_date,
    CASE 
        WHEN c.class_code = '1A' THEN 48
        WHEN c.class_code = '2A' THEN 96
        WHEN c.class_code = '3A' THEN 144
        ELSE 180
    END as available_seats,
    0 as waiting_list,
    CASE 
        WHEN c.class_code = '1A' THEN 48
        WHEN c.class_code = '2A' THEN 96
        WHEN c.class_code = '3A' THEN 144
        ELSE 180
    END as total_seats,
    CASE 
        WHEN c.class_code = '1A' THEN 0
        WHEN c.class_code = '2A' THEN 10
        WHEN c.class_code = '3A' THEN 15
        ELSE 20
    END as rac_seats,
    CASE 
        WHEN c.class_code = '1A' THEN 0
        WHEN c.class_code = '2A' THEN 10
        WHEN c.class_code = '3A' THEN 15
        ELSE 20
    END as rac_available
FROM trains t
CROSS JOIN classes c
CROSS JOIN generate_series(0, 30) i
WHERE t.type IN ('RAJDHANI', 'SHATABDI', 'DURONTO')
AND c.class_code IN ('1A', '2A', '3A')
AND NOT EXISTS (
    SELECT 1 FROM seat_availabilities sa 
    WHERE sa.train_id = t.id 
    AND sa.class_id = c.id 
    AND sa.travel_date = CURRENT_DATE + (i || ' days')::interval
);

-- Train Schedules
INSERT INTO train_schedules (
    train_id, effective_from, effective_to, active, schedule_type
)
SELECT 
    t.id,
    CURRENT_DATE,
    CURRENT_DATE + INTERVAL '365 days',
    true,
    'REGULAR'
FROM trains t
WHERE t.number IN (12951, 12269, 12259, 12839, 12123)
AND NOT EXISTS (
    SELECT 1 FROM train_schedules ts
    WHERE ts.train_id = t.id
);

-- Train Running Days (each train's schedule ID with running days)
-- For Rajdhani Express (12951) - Runs on all days except Sunday
INSERT INTO train_running_days (schedule_id, day_of_week)
SELECT 
    ts.id, dow::text
FROM train_schedules ts
JOIN trains t ON t.id = ts.train_id
CROSS JOIN (
    VALUES ('MONDAY'), ('TUESDAY'), ('WEDNESDAY'), ('THURSDAY'), ('FRIDAY'), ('SATURDAY')
) AS days(dow)
WHERE t.number = 12951
AND NOT EXISTS (
    SELECT 1 FROM train_running_days trd
    WHERE trd.schedule_id = ts.id AND trd.day_of_week = dow::text
);

-- For Duronto Express (12269) - Runs on Monday, Wednesday, Friday
INSERT INTO train_running_days (schedule_id, day_of_week)
SELECT 
    ts.id, dow::text
FROM train_schedules ts
JOIN trains t ON t.id = ts.train_id
CROSS JOIN (
    VALUES ('MONDAY'), ('WEDNESDAY'), ('FRIDAY')
) AS days(dow)
WHERE t.number = 12269
AND NOT EXISTS (
    SELECT 1 FROM train_running_days trd
    WHERE trd.schedule_id = ts.id AND trd.day_of_week = dow::text
);

-- For Shatabdi Express (12259) - Runs on all weekdays (Monday to Friday)
INSERT INTO train_running_days (schedule_id, day_of_week)
SELECT 
    ts.id, dow::text
FROM train_schedules ts
JOIN trains t ON t.id = ts.train_id
CROSS JOIN (
    VALUES ('MONDAY'), ('TUESDAY'), ('WEDNESDAY'), ('THURSDAY'), ('FRIDAY')
) AS days(dow)
WHERE t.number = 12259
AND NOT EXISTS (
    SELECT 1 FROM train_running_days trd
    WHERE trd.schedule_id = ts.id AND trd.day_of_week = dow::text
);

-- For Chennai Mail (12839) - Runs on all days
INSERT INTO train_running_days (schedule_id, day_of_week)
SELECT 
    ts.id, dow::text
FROM train_schedules ts
JOIN trains t ON t.id = ts.train_id
CROSS JOIN (
    VALUES ('MONDAY'), ('TUESDAY'), ('WEDNESDAY'), ('THURSDAY'), ('FRIDAY'), ('SATURDAY'), ('SUNDAY')
) AS days(dow)
WHERE t.number = 12839
AND NOT EXISTS (
    SELECT 1 FROM train_running_days trd
    WHERE trd.schedule_id = ts.id AND trd.day_of_week = dow::text
);

-- For Deccan Queen (12123) - Runs on all days except Sunday
INSERT INTO train_running_days (schedule_id, day_of_week)
SELECT 
    ts.id, dow::text
FROM train_schedules ts
JOIN trains t ON t.id = ts.train_id
CROSS JOIN (
    VALUES ('MONDAY'), ('TUESDAY'), ('WEDNESDAY'), ('THURSDAY'), ('FRIDAY'), ('SATURDAY')
) AS days(dow)
WHERE t.number = 12123
AND NOT EXISTS (
    SELECT 1 FROM train_running_days trd
    WHERE trd.schedule_id = ts.id AND trd.day_of_week = dow::text
);

-- Seats for each coach
-- This will generate seats for all coaches created above
INSERT INTO seats (
    coach_id, seat_number, row_number, is_window, is_aisle, status
)
SELECT 
    c.id as coach_id,
    CASE 
        WHEN cl.class_code = '1A' THEN 
            CONCAT(
                CASE 
                    WHEN (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 6 THEN 'W'
                    WHEN (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 4 THEN 'A'
                    ELSE 'M'
                END,
                LPAD(seat_pos::text, 2, '0')
            )
        WHEN cl.class_code = '2A' THEN 
            CONCAT(
                CASE 
                    WHEN (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 8 THEN 'W'
                    WHEN (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 6 THEN 'A'
                    ELSE 'M'
                END,
                LPAD(seat_pos::text, 2, '0')
            )
        WHEN cl.class_code = '3A' THEN 
            CONCAT(
                CASE 
                    WHEN (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 9 THEN 'W'
                    WHEN (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 7 THEN 'A'
                    ELSE 'M'
                END,
                LPAD(seat_pos::text, 2, '0')
            )
        ELSE 
            CONCAT(
                CASE 
                    WHEN (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 9 THEN 'W'
                    WHEN (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 7 THEN 'A'
                    ELSE 'M'
                END,
                LPAD(seat_pos::text, 2, '0')
            )
    END as seat_number,
    CEIL(seat_pos::float / c.seats_per_row) as row_number,
    CASE 
        WHEN cl.class_code = '1A' THEN 
            (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 6
        WHEN cl.class_code = '2A' THEN 
            (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 8
        WHEN cl.class_code = '3A' THEN 
            (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 9
        ELSE 
            (seat_pos % c.seats_per_row) = 1 OR (seat_pos % c.seats_per_row) = 9
    END as is_window,
    CASE 
        WHEN cl.class_code = '1A' THEN 
            (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 4
        WHEN cl.class_code = '2A' THEN 
            (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 6
        WHEN cl.class_code = '3A' THEN 
            (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 7
        ELSE 
            (seat_pos % c.seats_per_row) = 3 OR (seat_pos % c.seats_per_row) = 7
    END as is_aisle,
    'AVAILABLE' as status
FROM coaches c
JOIN classes cl ON c.class_id = cl.id
CROSS JOIN generate_series(1, c.total_seats) as seat_pos
WHERE NOT EXISTS (
    SELECT 1 FROM seats s
    WHERE s.coach_id = c.id
); 