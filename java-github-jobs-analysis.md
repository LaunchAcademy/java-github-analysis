In this challenge, you'll use JSON provided by an Application Programming Interface and perform some analysis using Java.

## Getting Started

```no-highlight
et get java-github-jobs-analysis
cd java-github-jobs-analysis
idea .
```

We have supplied a snapshot of the GitHub Jobs API in the file `positions.json` - use this file to complete the user stories below.

## Core User Stories

### Location, Location, Location!

```no-highlight
As a job seeker
I want to see a summary of jobs by location
So that I can identify the best place to live
```

Acceptance Criteria:

- Calculate the number of jobs per location in the JSON file
- Output the totals, displaying both the location and the total number of jobs
- Don't worry about locations that are the same place, formatted differently. You can consider those separate places -- and refactor later if you want the challenge!

### Companies

```no-highlight
As a job seeker
I want to see a summary of Company Postings
So that I can see if a company is hiring for a number of roles
```

Acceptance Criteria:

- Calculate the number of job postings per company in the JSON file
- Output the name of each company and the number of job postings associated with it.

### Write a GitHub Job

GitHub has hired you to write a command line tool for generating JSON to submit to their servers.

```no-highlight
As a job poster
I want to create a job JSON
So that it can be submitted later
```

Acceptance Criteria:

- After the statistics above are outputted, ask the user if they would like to submit a job
- If they choose to submit a job, prompt for a type, URL, title, and description (don't worry about the other fields)
- Output the results in JSON consistent with a single entry in `positions.json` in a file called `job.json`.

## Non-Core User Stories

### Only Jobs From June

```no-highlight
As a job seeker
I only want to see a summary of jobs from June
So that I can analyze specific jobs
```

Acceptance Criteria:

- Only Jobs created in June are considered when outputting the analysis above

### Word Frequency

```no-highlight
As a job seeker
I want to know the most common word in job descriptions
So that I know what I should talk about at job interviews
```

Acceptance Criteria:

- As part of the analysis, the 10 most frequently used words in the job descriptions are outputted
- Omit the following words from the analysis:
  - the
  - and
  - a
  - we
  - you

Implementation Details:

- You will likely have to strip out the HTML from the description
- We recommend using the [JSoup][jsoup] library for sanitizing the descriptions

[jsoup]: https://jsoup.org/
