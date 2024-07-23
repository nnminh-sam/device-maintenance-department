CREATE TABLE
  `roles` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `updated_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `deleted_at` timestamp
  );

CREATE TABLE
  `employees` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `first_name` text NOT NULL,
    `last_name` text NOT NULL,
    `citizen_id` text,
    `dob` date NOT NULL,
    `email` text NOT NULL,
    `password` text NOT NULL,
    `address` text,
    `phone` text NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `updated_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `deleted_at` timestamp,
    `role_id` int,
    `account_id` int
  );

CREATE TABLE
  `rooms` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) UNIQUE NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `updated_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `deleted_at` timestamp
  );

CREATE TABLE
  `devices` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `serial_number` text NOT NULL,
    `device_name` text NOT NULL,
    `description` text NOT NULL,
    `purchase_date` date,
    `assigned_date` date,
    `warranty_expiry_date` date,
    `device_status` text,
    `created_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `updated_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `deleted_at` timestamp,
    `room_id` int,
    `employee_id` int
  );

CREATE TABLE
  `requests` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `request_type` text,
    `description_before` text,
    `description_after` text,
    `status` text,
    `complete_date` date,
    `created_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `updated_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `deleted_at` timestamp,
    `requested_by` int,
    `device_id` int
  );

CREATE TABLE
  `maintenance_logs` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `device_id` int,
    `employee_id` int,
    `complete_date` date,
    `description` text,
    `created_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `updated_at` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    `deleted_at` timestamp
  );

CREATE UNIQUE INDEX `id` ON `roles` (`id`);

CREATE UNIQUE INDEX `name` ON `roles` (`name`);

ALTER TABLE `employees` ADD CONSTRAINT `employees_roles_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE CASCADE;

ALTER TABLE `devices` ADD CONSTRAINT `devices_employees_id_fk` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON UPDATE CASCADE;

ALTER TABLE `devices` ADD CONSTRAINT `devices_room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON UPDATE CASCADE;

ALTER TABLE `requests` ADD CONSTRAINT `requests_requested_by_fk` FOREIGN KEY (`requested_by`) REFERENCES `employees` (`id`) ON UPDATE CASCADE;

ALTER TABLE `requests` ADD CONSTRAINT `requests_device_id_fk` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`) ON UPDATE CASCADE;

ALTER TABLE `devices` ADD CONSTRAINT `maintenance_logs_device_id_fk` FOREIGN KEY (`id`) REFERENCES `maintenance_logs` (`device_id`) ON UPDATE CASCADE;

ALTER TABLE `employees` ADD CONSTRAINT `maintenance_logs_employee_id_fk` FOREIGN KEY (`id`) REFERENCES `maintenance_logs` (`employee_id`) ON UPDATE CASCADE;