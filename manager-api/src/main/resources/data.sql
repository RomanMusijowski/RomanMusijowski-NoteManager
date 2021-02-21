INSERT INTO NOTES VALUES (1, 'content sample', 1, NULL, NOW(), NULL );
INSERT INTO NOTES VALUES (2, 'content sample2', 1, NULL, NOW(), NULL );
INSERT INTO NOTES VALUES (3, 'content sample3', 1, NULL, NOW(), NULL );
INSERT INTO NOTES VALUES (4, 'content sample4', 3, NULL,
                                                    NOW() - interval '8 day',
                                                    NOW() - interval '3 day');
INSERT INTO NOTES VALUES (5, 'content sample5', 2, NOW() - interval '3 day',
                                                    NOW() - interval '8 day',
                                                    NOW() - interval '4 day' );



INSERT INTO NOTE_SNAPSHOTS VALUES (1, 'content sample4 previous vers1', 1, 4, NOW() - interval '7 day');
INSERT INTO NOTE_SNAPSHOTS VALUES (2, 'content sample4 previous vers2', 2, 4, NOW() - interval '5 day');
INSERT INTO NOTE_SNAPSHOTS VALUES (3, 'content sample5 previous vers1', 1, 5, NOW() - interval '5 day');


